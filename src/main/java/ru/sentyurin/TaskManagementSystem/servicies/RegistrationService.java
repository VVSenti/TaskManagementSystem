package ru.sentyurin.TaskManagementSystem.servicies;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.repositories.PersonRepository;

@Service
public class RegistrationService {

	private final PersonRepository personRepository;

	@Autowired
	public RegistrationService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	@Transactional
	public void register(Person person) {
		personRepository.save(person);
	}
}
