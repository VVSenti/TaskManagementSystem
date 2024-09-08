package ru.sentyurin.TaskManagementSystem.util;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationErrorMessageBuilder {
	public static String makeValidationErrorMessage(BindingResult bindingResult) {
		StringBuilder errorMsg = new StringBuilder();
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors) {
			errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage())
					.append("; ");
		}
		return errorMsg.toString();
	}
}
