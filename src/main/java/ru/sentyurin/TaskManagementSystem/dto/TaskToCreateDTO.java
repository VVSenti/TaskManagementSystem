package ru.sentyurin.TaskManagementSystem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import ru.sentyurin.TaskManagementSystem.models.Priority;
import ru.sentyurin.TaskManagementSystem.models.Status;

public class TaskToCreateDTO {

	@NotEmpty
	private String title;

	@NotEmpty
	private String description;

	@NotNull
	private Status status;

	@NotNull
	private Priority priority;

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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getExecutorEmail() {
		return executorEmail;
	}

	public void setExecutorEmail(String executorEmail) {
		this.executorEmail = executorEmail;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
