package ru.sentyurin.TaskManagementSystem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class PersonRegistrationDTO {

	@NotEmpty(message = "E-mail cannot be empty")
	@Email(message = "Incorrect format")
	private String email;

	@NotEmpty(message = "Password cannot be empty")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
