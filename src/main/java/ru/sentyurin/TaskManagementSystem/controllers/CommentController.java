package ru.sentyurin.TaskManagementSystem.controllers;

import static ru.sentyurin.TaskManagementSystem.util.ValidationErrorMessageBuilder.makeValidationError;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.sentyurin.TaskManagementSystem.dto.CommentToCreateDTO;
import ru.sentyurin.TaskManagementSystem.dto.CommentToShowDTO;
import ru.sentyurin.TaskManagementSystem.models.Comment;
import ru.sentyurin.TaskManagementSystem.servicies.CommentService;
import ru.sentyurin.TaskManagementSystem.servicies.PersonService;
import ru.sentyurin.TaskManagementSystem.servicies.TaskService;
import ru.sentyurin.TaskManagementSystem.util.AutorizationException;
import ru.sentyurin.TaskManagementSystem.util.CommentErrorResponse;
import ru.sentyurin.TaskManagementSystem.util.CommentNotCreatedException;
import ru.sentyurin.TaskManagementSystem.util.CommentNotFoundException;

@RestController
@RequestMapping
public class CommentController {

	private final CommentService commentService;
	private final TaskService taskService;
	private final PersonService personService;
	private final ModelMapper modelMapper;

	@Autowired
	public CommentController(CommentService commentService, TaskService taskService,
			PersonService personService, ModelMapper modelMapper) {
		this.commentService = commentService;
		this.taskService = taskService;
		this.personService = personService;
		this.modelMapper = modelMapper;
	}

	@GetMapping("/comments/{id}")
	public CommentToShowDTO getComment(@PathVariable("id") int id) {
		Comment comment = commentService.findOne(id);
		return convertToCommentToShowDTO(comment);
	}

	@GetMapping("tasks/{id}/comments")
	public List<CommentToShowDTO> getCommentsForTask(@PathVariable("id") int id) {
		return commentService.findAllByTaskId(id).stream().map(this::convertToCommentToShowDTO)
				.toList();
	}

	@PostMapping("tasks/{id}/comments")
	public ResponseEntity<HttpStatus> createComment(@PathVariable("id") int id,
			@RequestBody @Valid CommentToCreateDTO commentDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new CommentNotCreatedException(makeValidationError(bindingResult));
		}
		Comment comment = new Comment();
		comment.setText(commentDTO.getText());
		comment.setTask(taskService.findOne(id));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		comment.setAuthor(personService.findOne(principal.getUsername()));
		commentService.save(comment);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") int id) {
		Comment comment = commentService.findOne(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String authorEmail = comment.getAuthor().getEmail();
		String userEmail = principal.getUsername();
		if (!authorEmail.equals(userEmail))
			throw new AutorizationException("User may delete only their own comments!");
		commentService.deleteById(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PatchMapping("/comments/{id}")
	public ResponseEntity<HttpStatus> editComment(@PathVariable("id") int id,
			@RequestBody @Valid CommentToCreateDTO commentDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new CommentNotCreatedException(makeValidationError(bindingResult));
			// TODO: change exception, make it more specific?
		}
		Comment commentToUpdate = commentService.findOne(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String authorEmail = commentToUpdate.getAuthor().getEmail();
		String userEmail = principal.getUsername();
		if (!authorEmail.equals(userEmail))
			throw new AutorizationException("User may edit only their own comments!");
		commentToUpdate.setText(commentDTO.getText());
		commentService.save(commentToUpdate);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	private CommentToShowDTO convertToCommentToShowDTO(Comment comment) {
		CommentToShowDTO commentDTO = modelMapper.map(comment, CommentToShowDTO.class);
		commentDTO.setTaskId(comment.getTask().getId());
		commentDTO.setAuthorId(comment.getAuthor().getId());
		return commentDTO;
	}

	@ExceptionHandler
	private ResponseEntity<CommentErrorResponse> handleException(CommentNotFoundException e) {
		CommentErrorResponse errorResponse = new CommentErrorResponse("Comment hasn't been found!",
				System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	private ResponseEntity<CommentErrorResponse> handleException(CommentNotCreatedException e) {
		CommentErrorResponse errorResponse = new CommentErrorResponse(e.getMessage(),
				System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	private ResponseEntity<CommentErrorResponse> handleException(AutorizationException e) {
		CommentErrorResponse errorResponse = new CommentErrorResponse(e.getMessage(),
				System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
	}
}
