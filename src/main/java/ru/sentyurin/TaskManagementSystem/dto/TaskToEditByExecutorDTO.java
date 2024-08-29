package ru.sentyurin.TaskManagementSystem.dto;

import javax.validation.constraints.NotNull;
import ru.sentyurin.TaskManagementSystem.models.Status;

public class TaskToEditByExecutorDTO {

	@NotNull
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
