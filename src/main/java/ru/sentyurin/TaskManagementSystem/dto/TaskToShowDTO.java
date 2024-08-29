package ru.sentyurin.TaskManagementSystem.dto;

import ru.sentyurin.TaskManagementSystem.models.Priority;
import ru.sentyurin.TaskManagementSystem.models.Status;

public class TaskToShowDTO {
	private int id;
	private String title;
	private String description;
	private Status status;
	private Priority priority;
	private String authorEmail;
	private String executorEmail;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
