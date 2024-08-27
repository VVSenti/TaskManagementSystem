package ru.sentyurin.TaskManagementSystem.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.sentyurin.TaskManagementSystem.models.Task;
import ru.sentyurin.TaskManagementSystem.dto.PersonDTO;
import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.repositories.PersonRepository;
import ru.sentyurin.TaskManagementSystem.util.PersonNotFoundException;

@Service
@Transactional(readOnly = true)
public class PersonService {

	private PersonRepository personRepository;

	@Autowired
	public PersonService(PersonRepository peopleRepository) {
		this.personRepository = peopleRepository;
	}

	public List<Person> findAll() {
		return personRepository.findAll();
	}

	public Person findOne(int id) {
		return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
	}

	public Person findOne(String email) {
		return personRepository.findByEmail(email).orElseThrow(PersonNotFoundException::new);
	}

}
