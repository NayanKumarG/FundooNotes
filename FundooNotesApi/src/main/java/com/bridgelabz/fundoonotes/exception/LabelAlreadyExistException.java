/**
 * @author Nayan Kumar G
 * purpose :To throw LabelException
 * date    :07-03-2020
 */
package com.bridgelabz.fundoonotes.exception;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class LabelAlreadyExistException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus statusCode;

	public LabelAlreadyExistException(String message, HttpStatus statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	
}
