/**
 * @author Nayan Kumar G
 * purpose : Controller for Users
 * date    :03-03-2020
 */
package com.bridgelabz.fundoonotes.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	
	private String message;

	/**
	 * 
	 * @param message to throw the message
	 */
	public UserException(String message) {
		super();
		this.message = message;
	}
}
