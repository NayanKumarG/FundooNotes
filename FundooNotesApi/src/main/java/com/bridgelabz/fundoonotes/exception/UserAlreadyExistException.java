/**
 * @author Nayan Kumar G
 * purpose :To throw UserAlreadyExist exception
 * date    :09-03-2020
 */
package com.bridgelabz.fundoonotes.exception;

public class UserAlreadyExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(String message) {
		super(message);
		
	}
}
