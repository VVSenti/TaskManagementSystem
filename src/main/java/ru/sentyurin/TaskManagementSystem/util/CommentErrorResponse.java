package ru.sentyurin.TaskManagementSystem.util;

public class CommentErrorResponse {
	private String message;
	private long timestamp;

	public CommentErrorResponse(String message, long timestamp) {
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public long getTimestamp() {
		return timestamp;
	}
}