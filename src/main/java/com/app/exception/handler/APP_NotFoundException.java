package com.app.exception.handler;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class APP_NotFoundException extends Exception {

	private int error_code = HttpStatus.NOT_FOUND.value();

	public APP_NotFoundException() {
		super();
	}

	public APP_NotFoundException(String errormessage) {
		super(errormessage);
	}

	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

}
