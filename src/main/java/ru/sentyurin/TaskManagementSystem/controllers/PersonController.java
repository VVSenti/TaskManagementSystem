package ru.sentyurin.TaskManagementSystem.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.sentyurin.TaskManagementSystem.dto.PersonDTO;
import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.servicies.PersonService;
import ru.sentyurin.TaskManagementSystem.util.PersonErrorResponse;
import ru.sentyurin.TaskManagementSystem.util.PersonNotFoundException;

@RestController
@RequestMapping("/people")
public class PersonController {

	private PersonService personService;

	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping("/current")
	public PersonDTO getCurrentPerson() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String email = ((UserDetails) principal).getUsername();
			Person person = personService.findOne(email);
			return convertToPersonDTO(person);
		}
		return null;
	}

	@GetMapping("")
	public List<PersonDTO> getPeople() {
		return personService.findAll().stream().map(this::convertToPersonDTO).toList();
	}

	@GetMapping("/{id}")
	public PersonDTO getPerson(@PathVariable("id") int id) {
		Person person = personService.findOne(id);
		return convertToPersonDTO(person);
	}

	private PersonDTO convertToPersonDTO(Person person) {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setEmail(person.getEmail());
		return personDTO;
	}

	@ExceptionHandler
	private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
		PersonErrorResponse errorResponse = new PersonErrorResponse(
				"Person hasn't been found!", new Date());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
