/**
 * @author Nayan Kumar G
 * purpose :To throw invalid user credential exception
 * date    :09-03-2020
 */
package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
@Getter
public class InvalidUserCredentialException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus statusCode;
	public InvalidUserCredentialException(String message, HttpStatus statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
}
