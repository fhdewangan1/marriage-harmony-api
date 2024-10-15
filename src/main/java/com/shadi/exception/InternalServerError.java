package com.shadi.exception;

public class InternalServerError extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InternalServerError() {
		super();
	}

	public InternalServerError(String message) {
		super(message);

	}
}
