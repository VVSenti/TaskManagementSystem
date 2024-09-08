package ru.sentyurin.TaskManagementSystem;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import ru.sentyurin.TaskManagementSystem.dto.PersonLoginDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

	@Autowired
	TestRestTemplate template;

	@Test
	void shouldReturnJWT() {
		PersonLoginDTO authDTO = new PersonLoginDTO("sentyurinvv@gmail.com", "Asd456");
		ResponseEntity<Map> entity = template.postForEntity("/auth/login", authDTO,
				Map.class);		
		Map<String, String> jwt = entity.getBody();
		assertTrue(!jwt.get("jwt-token").isEmpty());		
	}
}
