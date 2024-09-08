package ru.sentyurin.TaskManagementSystem.controllers;

import static ru.sentyurin.TaskManagementSystem.util.ValidationErrorMessageBuilder.makeValidationErrorMessage;

import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.sentyurin.TaskManagementSystem.dto.PersonLoginDTO;
import ru.sentyurin.TaskManagementSystem.dto.PersonRegistrationDTO;
import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.security.JWTUtil;
import ru.sentyurin.TaskManagementSystem.servicies.RegistrationService;
import ru.sentyurin.TaskManagementSystem.util.AuthErrorResponse;
import ru.sentyurin.TaskManagementSystem.util.AuthException;
import ru.sentyurin.TaskManagementSystem.util.PersonValidator;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final PersonValidator personValidator;
	private final RegistrationService registrationService;
	private final JWTUtil jwtUtil;
	private final ModelMapper modelMapper;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public AuthController(PersonValidator personValidator, RegistrationService registrationService,
			JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
		this.personValidator = personValidator;
		this.registrationService = registrationService;
		this.jwtUtil = jwtUtil;
		this.modelMapper = modelMapper;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/registration")
	public Map<String, String> registerNewPerson(
			@RequestBody @Valid PersonRegistrationDTO personDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new AuthException(makeValidationErrorMessage(bindingResult));
		}
		Person person = convertToPerson(personDTO);
		personValidator.validate(person, null);
		registrationService.register(person);
		String token = jwtUtil.generateToken(person.getEmail());
		return Map.of("jwt-token", token);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> performLogin(
			@RequestBody @Valid PersonLoginDTO authenticationDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new AuthException(makeValidationErrorMessage(bindingResult));
		}
		UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
				authenticationDTO.getEmail(), authenticationDTO.getPassword());
		try {
			authenticationManager.authenticate(authInputToken);
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(Map.of("message", "Incorrect email or password!"),
					HttpStatus.BAD_REQUEST);
		}

		String token = jwtUtil.generateToken(authenticationDTO.getEmail());
		return new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK);
	}

	private Person convertToPerson(PersonRegistrationDTO personDTO) {
		Person person = modelMapper.map(personDTO, Person.class);
		return person;
	}

	@ExceptionHandler
	private ResponseEntity<AuthErrorResponse> handleException(AuthException e) {
		AuthErrorResponse errorResponse = new AuthErrorResponse(e.getMessage(), new Date());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
