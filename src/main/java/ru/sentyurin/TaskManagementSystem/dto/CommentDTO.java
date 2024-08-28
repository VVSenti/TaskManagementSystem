package ru.sentyurin.TaskManagementSystem.dto;

import javax.validation.constraints.NotNull;

public class CommentDTO {

	@NotNull
	private int taskId;

	@NotNull
	private String text;

	public CommentDTO() {
	}

	public int getTaskId() {
		return taskId;
	}

	public String getText() {
		return text;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void setText(String text) {
		this.text = text;
	}

}
