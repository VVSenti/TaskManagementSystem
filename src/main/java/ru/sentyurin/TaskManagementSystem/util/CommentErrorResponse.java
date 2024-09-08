package ru.sentyurin.TaskManagementSystem.util;

import java.util.Date;

public class CommentErrorResponse {
	private String message;
	private Date timestamp;

	public CommentErrorResponse(String message, Date timestamp) {
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}