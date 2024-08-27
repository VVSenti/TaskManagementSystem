package ru.sentyurin.TaskManagementSystem.util;

public class TaskErrorResponse {
	private String message;
	private long timestamp;

	public TaskErrorResponse(String message, long timestamp) {
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
