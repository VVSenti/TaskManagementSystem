package ru.sentyurin.TaskManagementSystem.util;

import java.util.Date;

public class TaskErrorResponse {
	private String message;
	private Date timestamp;

	public TaskErrorResponse(String message, Date timestamp) {
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
