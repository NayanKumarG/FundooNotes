package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MailNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private HttpStatus statusCode;

	public MailNotFoundException(String message, HttpStatus statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	
	

	
	
}
