package ru.sentyurin.TaskManagementSystem.dto;

import ru.sentyurin.TaskManagementSystem.models.Priority;
import ru.sentyurin.TaskManagementSystem.models.Status;

public class TaskToEditDTO {

	private String title;

	private String description;

	private Priority priority;

	private Status status;

	private String executorEmail;

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Priority getPriority() {
		return priority;
	}

	public Status getStatus() {
		return status;
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

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setExecutorEmail(String executorEmail) {
		this.executorEmail = executorEmail;
	}

}
