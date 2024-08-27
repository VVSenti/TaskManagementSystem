package ru.sentyurin.TaskManagementSystem.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.sentyurin.TaskManagementSystem.dto.PersonRegistrationDTO;
import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.security.JWTUtil;
import ru.sentyurin.TaskManagementSystem.servicies.RegistrationService;
import ru.sentyurin.TaskManagementSystem.util.PersonValidator;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final PersonValidator personValidator;
	private final RegistrationService registrationService;
	private final JWTUtil jwtUtil;
	private final ModelMapper modelMapper;

	@Autowired
	public AuthController(PersonValidator personValidator, RegistrationService registrationService,
			JWTUtil jwtUtil, ModelMapper modelMapper) {
		this.personValidator = personValidator;
		this.registrationService = registrationService;
		this.jwtUtil = jwtUtil;
		this.modelMapper = modelMapper;
	}

	@PostMapping("/registration")
	public Map<String, String> registerNewPerson(@RequestBody PersonRegistrationDTO personDTO) {
		System.out.println(0);
		Person person = convertToPerson(personDTO);
		System.out.println(1);
		personValidator.validate(person, null);
		System.out.println(2);
		
		registrationService.register(person);
		System.out.println(3);
		
		String token = jwtUtil.generateToken(person.getEmail());
		System.out.println(4);
		return Map.of("jwt-token", token);
	}
	
	private Person convertToPerson(PersonRegistrationDTO personDTO) {
		Person person = modelMapper.map(personDTO, Person.class);
		
		return person;
	}

}
