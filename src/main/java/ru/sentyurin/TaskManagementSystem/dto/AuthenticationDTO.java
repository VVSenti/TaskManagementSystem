package ru.sentyurin.TaskManagementSystem.dto;

import javax.validation.constraints.NotEmpty;

public class AuthenticationDTO {
	@NotEmpty
	private String username;

	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
