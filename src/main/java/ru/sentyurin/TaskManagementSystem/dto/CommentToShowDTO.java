package ru.sentyurin.TaskManagementSystem.dto;

import javax.validation.constraints.NotNull;

public class CommentToShowDTO {

	private int id;
	private int taskId;
	private int authorId;
	private String text;

	public int getId() {
		return id;
	}

	public int getTaskId() {
		return taskId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public String getText() {
		return text;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public void setText(String text) {
		this.text = text;
	}

}
