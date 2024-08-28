package ru.sentyurin.TaskManagementSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import ru.sentyurin.TaskManagementSystem.dto.TaskDTO;
import ru.sentyurin.TaskManagementSystem.models.Priority;
import ru.sentyurin.TaskManagementSystem.models.Status;
import ru.sentyurin.TaskManagementSystem.models.Task;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

	@Autowired
	TestRestTemplate template;

	@Test
	void shouldReturnAllTasks() {
		ResponseEntity<TaskDTO[]> entity = template.getForEntity("/tasks", TaskDTO[].class);
//
//        assertEquals(HttpStatus.OK,entity.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());
//
//		TaskDTO[] tasks = entity.getBody();
//        assertTrue(tasks.size() >= 1);

//        assertEquals("97 Things Every Java Programmer Should Know",books[0].getTitle());
//        assertEquals("Spring Boot: Up and Running",books[1].getTitle());
//        assertEquals("Hacking with Spring Boot 2.3: Reactive Edition",books[2].getTitle());
	}

	@Test
	void shouldReturnFirstTask() {
		ResponseEntity<TaskDTO> entity = template.getForEntity("/tasks/1", TaskDTO.class);

//        assertEquals(HttpStatus.OK,entity.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());

		TaskDTO task = entity.getBody();
		assertEquals("Test task", task.getTitle());
		assertEquals("Make REST API", task.getDescription());
		assertEquals(Status.Waiting, task.getStatus());
		assertEquals(Priority.High, task.getPriority());
		assertEquals("sentyurinvv@gmail.com", task.getAuthorEmail());
	}
}
