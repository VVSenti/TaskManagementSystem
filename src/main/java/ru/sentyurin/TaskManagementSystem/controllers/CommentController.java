package ru.sentyurin.TaskManagementSystem.controllers;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.sentyurin.TaskManagementSystem.dto.CommentDTO;
import ru.sentyurin.TaskManagementSystem.models.Comment;
import ru.sentyurin.TaskManagementSystem.models.Task;
import ru.sentyurin.TaskManagementSystem.servicies.CommentService;
import ru.sentyurin.TaskManagementSystem.servicies.TaskService;
import ru.sentyurin.TaskManagementSystem.util.CommentErrorResponse;
import ru.sentyurin.TaskManagementSystem.util.CommentNotCreatedException;
import ru.sentyurin.TaskManagementSystem.util.CommentNotFoundException;
import ru.sentyurin.TaskManagementSystem.util.PersonErrorResponse;
import ru.sentyurin.TaskManagementSystem.util.PersonNotFoundException;
import ru.sentyurin.TaskManagementSystem.util.TaskNotCreatedException;

@RestController
@RequestMapping("/comments")
public class CommentController {

	private final CommentService commentService;
	private final TaskService taskService;
	private final ModelMapper modelMapper;

	@Autowired
	public CommentController(CommentService commentService, TaskService taskService,
			ModelMapper modelMapper) {
		this.commentService = commentService;
		this.taskService = taskService;
		this.modelMapper = modelMapper;
	}

	@GetMapping("/one/{id}")
	public CommentDTO getComment(@PathVariable("id") int id) {
		Comment comment = commentService.findOne(id);
		return convertToCommentDTO(comment);
	}

	@GetMapping("/{id}")
	public List<CommentDTO> getCommentsForTask(@PathVariable("id") int id) {
		return commentService.findAllByTaskId(id).stream().map(this::convertToCommentDTO).toList();
	}

	@PostMapping
	public ResponseEntity<HttpStatus> createComment(@RequestBody @Valid CommentDTO commentDTO,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder errorMsg = new StringBuilder();
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage())
						.append(";");
			}
			throw new CommentNotCreatedException(errorMsg.toString());
		}
		Comment comment = new Comment();
		comment.setText(commentDTO.getText());
		comment.setTask(taskService.findOne(commentDTO.getTaskId()));
		commentService.save(comment);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	private CommentDTO convertToCommentDTO(Comment comment) {
		CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
		commentDTO.setTaskId(comment.getTask().getId());
		return commentDTO;
	}

	@ExceptionHandler
	private ResponseEntity<CommentErrorResponse> handleException(CommentNotFoundException e) {
		CommentErrorResponse errorResponse = new CommentErrorResponse("Comment hasn't been found!",
				System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
