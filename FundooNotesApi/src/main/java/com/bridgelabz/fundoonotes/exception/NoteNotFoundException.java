/**
 * @author Nayan Kumar G
 * purpose :Custom exception for Note Module
 * date    :05-03-2020
 */
package com.bridgelabz.fundoonotes.exception;
import lombok.Getter;

@Getter
public class NoteNotFoundException extends RuntimeException{
	
	
	private static final long serialVersionUID = 1L;
	private String message;

	/**
	 * 
	 * @param message from exception
	 */
	public NoteNotFoundException(String message) {
		super();
		this.message = message;
	}
	
	

}
