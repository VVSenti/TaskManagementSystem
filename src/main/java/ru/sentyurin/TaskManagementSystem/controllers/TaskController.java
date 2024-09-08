package ru.sentyurin.TaskManagementSystem.controllers;

import static ru.sentyurin.TaskManagementSystem.util.ValidationErrorMessageBuilder.makeValidationErrorMessage;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.sentyurin.TaskManagementSystem.dto.TaskToShowDTO;
import ru.sentyurin.TaskManagementSystem.dto.TaskToCreateDTO;
import ru.sentyurin.TaskManagementSystem.dto.TaskToEditDTO;
import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.models.Task;
import ru.sentyurin.TaskManagementSystem.security.ValidationUtil;
import ru.sentyurin.TaskManagementSystem.servicies.PersonService;
import ru.sentyurin.TaskManagementSystem.servicies.TaskService;
import ru.sentyurin.TaskManagementSystem.util.PersonErrorResponse;
import ru.sentyurin.TaskManagementSystem.util.PersonNotFoundException;
import ru.sentyurin.TaskManagementSystem.util.TaskErrorResponse;
import ru.sentyurin.TaskManagementSystem.util.TaskNotCreatedException;
import ru.sentyurin.TaskManagementSystem.util.TaskNotEditedException;
import ru.sentyurin.TaskManagementSystem.util.TaskNotFoundException;

@RestController
public class TaskController {

	private final TaskService taskService;
	private final PersonService personService;
	private final ModelMapper modelMapper;
	private final ValidationUtil validationUtil;

	@Autowired
	public TaskController(TaskService taskService, PersonService personService,
			ModelMapper modelMapper, ValidationUtil validationUtil) {
		this.taskService = taskService;
		this.personService = personService;
		this.modelMapper = modelMapper;
		this.validationUtil = validationUtil;
	}

	@GetMapping("/tasks")
	public List<TaskToShowDTO> getAllTasks() {
		return taskService.findAll().stream().map(this::convertToTaskToShowDTO).toList();
	}

	@GetMapping("/tasks/{id}")
	public TaskToShowDTO getTask(@PathVariable("id") int taskId) {
		return convertToTaskToShowDTO(taskService.findOne(taskId));
	}

	@PostMapping("/tasks")
	public ResponseEntity<HttpStatus> createTask(@RequestBody @Valid TaskToCreateDTO taskDTO,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new TaskNotCreatedException(makeValidationErrorMessage(bindingResult));
		}
		Task task = modelMapper.map(taskDTO, Task.class);
		task.setAuthor(validationUtil.getCurrentUser());
		String executorEmail = taskDTO.getExecutorEmail();
		if (executorEmail != null)
			task.setExecutor(personService.findOne(executorEmail));
		taskService.save(task);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PatchMapping("/tasks/{id}")
	public ResponseEntity<HttpStatus> editTask(@PathVariable("id") int id,
			@RequestBody @Valid TaskToEditDTO taskDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new TaskNotEditedException(makeValidationErrorMessage(bindingResult));
		}
		Task oldTask = taskService.findOne(id);
		if (validationUtil.isCurrentUserTaskExecutor(oldTask)) {
			oldTask.setStatus(taskDTO.getStatus());
			taskService.save(oldTask);
			return ResponseEntity.ok(HttpStatus.OK);
		}
		if (validationUtil.isCurrentUserTaskAuthor(oldTask)) {
			Task taskEdits = convertToTaskForEditing(taskDTO);
			pushEditsToTask(oldTask, taskEdits);
			taskService.save(oldTask);
			return ResponseEntity.ok(HttpStatus.OK);
		}
		throw new TaskNotEditedException("Only the author and the executor may edit task!");
	}

	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") int taskId) {
		Task task = taskService.findOne(taskId);
		if (!validationUtil.isCurrentUserTaskAuthor(task))
			throw new TaskNotEditedException("Only the author may delete their tasks!");
		taskService.delete(task);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping("/tasks/author")
	public List<TaskToShowDTO> getAllTasksOfAuthor(@RequestParam("id") int id) {
		System.out.println(0);
		Person author = personService.findOne(id);
		System.out.println(1);
		return taskService.findByAuthor(author).stream().map(this::convertToTaskToShowDTO).toList();
	}

//	@GetMapping("/author")
//	public List<TaskToShowDTO> getAllTasksOfAuthor(@RequestParam("email") String email) {
//		Person author = personService.findOne(email);
//		return taskService.findByAuthor(author).stream().map(this::convertToTaskToShowDTO).toList();
//	}

	@GetMapping("/tasks/executor")
	public List<TaskToShowDTO> getAllTasksOfExecutor(@RequestParam("id") int id) {
		Person executor = personService.findOne(id);
		return taskService.findByExecutor(executor).stream().map(this::convertToTaskToShowDTO)
				.toList();
	}

//	@GetMapping("/executor")
//	public List<TaskToShowDTO> getAllTasksOfExecutor(@RequestParam("email") String email) {
//		Person executor = personService.findOne(email);
//		return taskService.findByExecutor(executor).stream().map(this::convertToTaskToShowDTO)
//				.toList();
//	}

	private TaskToShowDTO convertToTaskToShowDTO(Task task) {
		return modelMapper.map(task, TaskToShowDTO.class);
	}

	private void pushEditsToTask(Task oldTask, Task taskEdits) {
		Field[] fields = Task.class.getDeclaredFields();
		for (Field field : fields) {
			if ("id".equals(field.getName()))
				continue;
			field.setAccessible(true);
			try {
				Object value = field.get(taskEdits);
				if (value != null)
					field.set(oldTask, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			field.setAccessible(false);
		}
	}

	private Task convertToTaskForEditing(TaskToEditDTO taskDTO) {
		Task task = modelMapper.map(taskDTO, Task.class);
		String executorEmail = taskDTO.getExecutorEmail();
		if (executorEmail != null)
			task.setExecutor(personService.findOne(executorEmail));
		return task;
	}

	@ExceptionHandler
	private ResponseEntity<TaskErrorResponse> handleException(TaskNotFoundException e) {
		TaskErrorResponse errorResponse = new TaskErrorResponse("Task hasn't been found",
				new Date());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	private ResponseEntity<TaskErrorResponse> handlException(TaskNotCreatedException e) {
		TaskErrorResponse errorResponse = new TaskErrorResponse(e.getMessage(), new Date());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	private ResponseEntity<TaskErrorResponse> handleException(TaskNotEditedException e) {
		TaskErrorResponse errorResponse = new TaskErrorResponse(e.getMessage(), new Date());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
		PersonErrorResponse errorResponse = new PersonErrorResponse("There is no such user!",
				new Date());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	private ResponseEntity<PersonErrorResponse> handleException(AccessDeniedException e) {
		PersonErrorResponse errorResponse = new PersonErrorResponse("Access is denied!",
				new Date());
		return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
	}
}