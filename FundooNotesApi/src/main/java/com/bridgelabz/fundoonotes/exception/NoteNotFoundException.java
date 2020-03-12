/**
 * @author Nayan Kumar G
 * purpose :Custom exception for Note Module
 * date    :05-03-2020
 */
package com.bridgelabz.fundoonotes.exception;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NoteNotFoundException extends RuntimeException{
	
	
	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus statusCode;

	public NoteNotFoundException(String message, HttpStatus statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	
	

}
