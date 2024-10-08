package ru.sentyurin.TaskManagementSystem.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.repositories.PersonRepository;
import ru.sentyurin.TaskManagementSystem.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
	
	private final PersonRepository personRepository;

	@Autowired
	public PersonDetailsService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Person> person = personRepository.findByEmail(username);
		if (person.isEmpty()) throw new UsernameNotFoundException("User not found!");
		return new PersonDetails(person.get());		
	}	

}
