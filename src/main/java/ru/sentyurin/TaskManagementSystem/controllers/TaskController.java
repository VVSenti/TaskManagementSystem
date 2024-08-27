package ru.sentyurin.TaskManagementSystem.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.HttpResource;

import ru.sentyurin.TaskManagementSystem.dto.TaskDTO;
import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.models.Task;
import ru.sentyurin.TaskManagementSystem.servicies.PersonDetailsService;
import ru.sentyurin.TaskManagementSystem.servicies.PersonService;
import ru.sentyurin.TaskManagementSystem.servicies.TaskService;
import ru.sentyurin.TaskManagementSystem.util.TaskErrorResponse;
import ru.sentyurin.TaskManagementSystem.util.TaskNotCreatedException;
import ru.sentyurin.TaskManagementSystem.util.TaskNotFoundException;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private TaskService taskService;
	private PersonService personService;

	@Autowired
	public TaskController(TaskService taskService, PersonService personService) {
		this.taskService = taskService;
		this.personService = personService;
	}

	@GetMapping("")
	public List<TaskDTO> getAllTasks() {
		return taskService.findAll().stream().map(x -> convertToTaskDTO(x)).toList();
	}

	@GetMapping("/{id}")
	public TaskDTO getTask(@PathVariable("id") int taskId) {
		return convertToTaskDTO(taskService.findOne(taskId));
	}

	@PostMapping
	public ResponseEntity<HttpStatus> create(@RequestBody @Valid TaskDTO taskDTO,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder errorMsg = new StringBuilder();
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage())
						.append(";");
			}
			throw new TaskNotCreatedException(errorMsg.toString());
		}
		System.out.println("/nASDASDASD/n");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		taskDTO.setAuthorEmail(principal.getUsername());
		Task task = convertToTask(taskDTO);
		taskService.save(task);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	private TaskDTO convertToTaskDTO(Task task) {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setTitle(task.getTitle());
		taskDTO.setDescription(task.getDescription());
		taskDTO.setStatus(task.getStatus());
		taskDTO.setPriority(task.getPriority());
		taskDTO.setAuthorEmail(task.getAuthor().getEmail());
		Person executor = task.getExecutor();
		if (executor != null)
			taskDTO.setExecutorEmail(executor.getEmail());
		return taskDTO;
	}

	private Task convertToTask(TaskDTO taskDTO) {
		Task task = new Task();
		task.setTitle(taskDTO.getTitle());
		task.setDescription(taskDTO.getDescription());
		task.setStatus(taskDTO.getStatus());
		task.setPriority(taskDTO.getPriority());
		task.setAuthor(personService.findOne(taskDTO.getAuthorEmail()));
		String executorEmail = taskDTO.getExecutorEmail();
		if (!executorEmail.isEmpty())
			task.setExecutor(personService.findOne(executorEmail));
		return task;
	}
	
	@ExceptionHandler
	private ResponseEntity<TaskErrorResponse> handleException(TaskNotFoundException e){
		TaskErrorResponse errorResponse = new TaskErrorResponse("Task hasn't been found",
				System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	private ResponseEntity<TaskErrorResponse> handlException(TaskNotCreatedException e) {
		TaskErrorResponse errorResponse = new TaskErrorResponse(e.getMessage(),
				System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
