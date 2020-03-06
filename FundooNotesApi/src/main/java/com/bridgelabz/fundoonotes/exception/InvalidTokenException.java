package com.bridgelabz.fundoonotes.exception;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String message;
	private String msg = "invalid token Exception";

	public InvalidTokenException(String message) {
		super();
		this.message = message;
	}
	
	public String getMessage()
	{
		return msg;
	}
	
}
