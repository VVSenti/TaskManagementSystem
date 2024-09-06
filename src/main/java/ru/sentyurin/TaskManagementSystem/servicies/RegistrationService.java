package ru.sentyurin.TaskManagementSystem.servicies;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.repositories.PersonRepository;

@Service
public class RegistrationService {

	private final PersonRepository personRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
		this.personRepository = personRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void register(Person person) {
		person.setPassword(passwordEncoder.encode(person.getPassword()));
		personRepository.save(person);
	}
}
