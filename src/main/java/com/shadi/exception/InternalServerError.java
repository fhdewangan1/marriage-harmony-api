package com.shadi.exception;

import java.time.LocalDateTime;

public class InternalServerError extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final String errorCode; // Optional error code
	private final LocalDateTime timestamp; // Timestamp of the error

	public InternalServerError() {
		super("An internal server error occurred.");
		this.errorCode = "500"; // Default error code for internal server error
		this.timestamp = LocalDateTime.now();
	}

	public InternalServerError(String message) {
		super(message);
		this.errorCode = "500"; // Default error code
		this.timestamp = LocalDateTime.now();
	}

	public InternalServerError(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = "500"; // Default error code
		this.timestamp = LocalDateTime.now();
	}

	public InternalServerError(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode; // Custom error code
		this.timestamp = LocalDateTime.now();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}
