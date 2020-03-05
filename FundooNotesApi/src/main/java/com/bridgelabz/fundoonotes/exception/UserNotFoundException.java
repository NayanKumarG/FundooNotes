/**
 * @author Nayan Kumar G
 * purpose :exception for user not found
 * date    :04-03-2020
 */
package com.bridgelabz.fundoonotes.exception;
import lombok.Getter;
@Getter
public class UserNotFoundException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	private String message;

	public UserNotFoundException(String message) {
		super();
		this.message = message;
	}
}
