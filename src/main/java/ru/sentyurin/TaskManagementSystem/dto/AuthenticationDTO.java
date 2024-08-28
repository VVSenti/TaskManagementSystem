package ru.sentyurin.TaskManagementSystem.dto;

import javax.validation.constraints.NotEmpty;

public class AuthenticationDTO {
	@NotEmpty
	private String username;
	@NotEmpty
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

	public AuthenticationDTO() {
	}

	public AuthenticationDTO(@NotEmpty String username, @NotEmpty String password) {
		this.username = username;
		this.password = password;
	}
}
