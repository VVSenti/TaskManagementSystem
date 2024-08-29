package ru.sentyurin.TaskManagementSystem.dto;

import javax.validation.constraints.NotEmpty;

public class CommentToCreateDTO {

	@NotEmpty(message = "Comment should not be empty!")
	private String text;

	public CommentToCreateDTO() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
