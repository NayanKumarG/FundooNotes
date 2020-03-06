package com.bridgelabz.fundoonotes.exception;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String message;

	public InvalidTokenException(String message) {
		super();
		this.message = message;
	}
	
}
