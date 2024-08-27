package ru.sentyurin.TaskManagementSystem.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.servicies.PersonService;

@Component
public class PersonValidator implements Validator {
	
	private final PersonService personService;
	
	@Autowired
	public PersonValidator(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;
		try {
			personService.findOne(person.getEmail());
		} catch (PersonNotFoundException e) {
			return;
		}		
		errors.rejectValue("username", "", "Such user already exists!");
		
	}

}
