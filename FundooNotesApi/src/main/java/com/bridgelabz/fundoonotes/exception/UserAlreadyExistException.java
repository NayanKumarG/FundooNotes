/**
 * @author Nayan Kumar G
 * purpose :To throw UserAlreadyExist exception
 * date    :09-03-2020
 */
package com.bridgelabz.fundoonotes.exception;


import org.springframework.http.HttpStatus;

import lombok.Getter;
@Getter
public class UserAlreadyExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus statusCode;
	public UserAlreadyExistException(String message , HttpStatus statusCode) {
		this.message = message;
		this.statusCode= statusCode;
		
	}
}
