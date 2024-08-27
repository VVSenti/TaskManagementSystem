package ru.sentyurin.TaskManagementSystem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class PersonDTO {
	
	@NotEmpty
	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
