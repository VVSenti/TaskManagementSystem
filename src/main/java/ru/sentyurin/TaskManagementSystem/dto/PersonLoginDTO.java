package ru.sentyurin.TaskManagementSystem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class PersonLoginDTO {
	@Email(message = "Incorrect format")
	@NotEmpty(message = "E-mail cannot be empty")
	private String email;
	@NotEmpty(message = "Password cannot be empty")
	private String password;

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public PersonLoginDTO() {
	}

	public PersonLoginDTO(@NotEmpty String email, @NotEmpty String password) {
		this.email = email;
		this.password = password;
	}
}
