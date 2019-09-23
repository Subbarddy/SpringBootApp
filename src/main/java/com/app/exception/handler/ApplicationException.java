package com.app.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationException {
	private final Logger logger = LoggerFactory.getLogger(ApplicationException.class);

	@ExceptionHandler(value = APP_NotFoundException.class)
	public ResponseEntity<ErrorResponse> NotFoundException(APP_NotFoundException exception) {

		logger.error(exception.getLocalizedMessage(), exception);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(exception.getLocalizedMessage());
		errorResponse.setHttpStatus(exception.getError_code());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);

	}
}
