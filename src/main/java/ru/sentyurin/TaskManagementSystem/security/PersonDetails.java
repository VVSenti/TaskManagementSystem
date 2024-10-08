package ru.sentyurin.TaskManagementSystem.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ru.sentyurin.TaskManagementSystem.models.Person;

public class PersonDetails implements UserDetails {
	private final Person person;

	public PersonDetails(Person person) {
		this.person = person;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return person.getPassword();
	}

	@Override
	public String getUsername() {
		return person.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Person getPerson() {
		return person;
	}

}
