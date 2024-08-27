package ru.sentyurin.TaskManagementSystem.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ru.sentyurin.TaskManagementSystem.models.Comment;
import ru.sentyurin.TaskManagementSystem.models.Priority;
import ru.sentyurin.TaskManagementSystem.models.Status;

public class TaskDTO {
	@NotEmpty
	private String title;
	@NotEmpty
	private String description;
	@NotNull
	private Status status;
	@NotNull
	private Priority priority;
	private String authorEmail;
	private String executorEmail;
	// private List<Comment> comments = new ArrayList<>();
	
	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Status getStatus() {
		return status;
	}

	public Priority getPriority() {
		return priority;
	}

	public String getAuthorEmail() {
		return authorEmail;
	}

	public String getExecutorEmail() {
		return executorEmail;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	public void setExecutorEmail(String executorEmail) {
		this.executorEmail = executorEmail;
	}

}
