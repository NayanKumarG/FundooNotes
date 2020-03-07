/**
 * @author Nayan Kumar G
 * purpose :to throw LabelNotfoundexception
 * date    :07-03-2020
 */
package com.bridgelabz.fundoonotes.exception;

import lombok.Getter;

@Getter
public class LabelNotFoundException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	private String message;

	public LabelNotFoundException(String message) {
		super();
		this.message = message;
	}
	
}
