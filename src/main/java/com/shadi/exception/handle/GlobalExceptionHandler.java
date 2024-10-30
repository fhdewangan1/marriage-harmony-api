package com.shadi.exception.handle;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.shadi.exception.AccessDeniedException;
import com.shadi.exception.CustomException;
import com.shadi.exception.GenericException;
import com.shadi.exception.InternalServerError;
import com.shadi.exception.NotFoundException;
import com.shadi.utils.AppConstants;
import com.shadi.utils.ConstantValues;
import com.shadi.utils.ErrorResponse;
import com.shadi.utils.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ProblemDetail> handleGenericException(GenericException exception) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setProperty(ConstantValues.MESSAGE, exception.getMessage());
		problemDetail.setProperty(ConstantValues.STATUS, ConstantValues.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setType(ConstantValues.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Map<Object, Object>> noDataAvailable(NotFoundException notFoundException,
			WebRequest request) {
		CustomException customException = new CustomException(AppConstants.Not_Found,
				AppConstants.Not_Found_desc,
				LocalDateTime.now(), notFoundException.getMessage(), request.getDescription(false));

		Map<Object, Object> notFound = new HashMap<>();
		notFound.put(AppConstants.statusCode, customException.getStatusCode());
		notFound.put(AppConstants.status, customException.getStatus());
		notFound.put(AppConstants.timeStamp, customException.getTimestamp().toString());
		notFound.put(AppConstants.statusMessage, customException.getMessage());
		notFound.put(AppConstants.description, customException.getDescription());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
	}

	@ExceptionHandler(InternalServerError.class)
	public ResponseEntity<Map<Object, Object>> internalServerError(InternalServerError internalServerError,
			WebRequest request) {
		CustomException customException = new CustomException(AppConstants.Internal_Server_Error,
				AppConstants.Internal_Server_Error_desc, LocalDateTime.now(),
				internalServerError.getMessage(), request.getDescription(false));

		Map<Object, Object> internalServerErrorMap = new HashMap<>();
		internalServerErrorMap.put(AppConstants.statusCode, customException.getStatusCode());
		internalServerErrorMap.put(AppConstants.status, customException.getStatus());
		internalServerErrorMap.put(AppConstants.timeStamp, customException.getTimestamp().toString());
		internalServerErrorMap.put(AppConstants.statusMessage, customException.getMessage());
		internalServerErrorMap.put(AppConstants.description, customException.getDescription());

		return ResponseEntity.internalServerError().body(internalServerErrorMap);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
				ResponseStructure.createErrorStructure(HttpStatus.FORBIDDEN,
						HttpStatus.FORBIDDEN.value(),
						ex.getMessage(), request.getDescription(false)));
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleException(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse();

		// Set the details of the error response
		errorResponse.setMessage("An unexpected error occurred."); // General error message
		errorResponse.setDetails(e.getMessage()); // Specific error message

		// Log the exception for debugging
		System.err.println("Exception occurred: " + e);

		return errorResponse;
	}
}
