package ru.sentyurin.TaskManagementSystem.security;

import org.aspectj.weaver.patterns.PerSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.models.Task;
import ru.sentyurin.TaskManagementSystem.servicies.PersonService;

@Component
public class ValidationUtil {
	
	private final PersonService personService;
	
	@Autowired	
	public ValidationUtil(PersonService personService) {
		this.personService = personService;
	}

	public Boolean isCurrentUserTaskAuthor(Task task) {
		return getCurrentUserEmail().equals(task.getAuthor().getEmail());
	}
	
	public Boolean isCurrentUserTaskExecutor(Task task) {
		Person executor = task.getExecutor();
		if (executor == null) return false;
		return getCurrentUserEmail().equals(executor.getEmail());
	}
	
	public Person getCurrentUser() {
		return personService.findOne(getCurrentUserDetails().getUsername());
	}
	
	private String getCurrentUserEmail() {
		return getCurrentUserDetails().getUsername();
	}
	
	private UserDetails getCurrentUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (UserDetails) authentication.getPrincipal();
	}
}
