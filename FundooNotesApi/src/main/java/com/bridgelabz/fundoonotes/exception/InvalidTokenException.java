/**
 * @author Nayan Kumar G
 * purpose :To throw invalid token exception
 * date    :09-03-2020
 */
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
