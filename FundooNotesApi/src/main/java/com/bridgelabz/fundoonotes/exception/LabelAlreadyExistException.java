/**
 * @author Nayan Kumar G
 * purpose :To throw LabelException
 * date    :07-03-2020
 */
package com.bridgelabz.fundoonotes.exception;
import lombok.Getter;

@Getter
public class LabelAlreadyExistException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	private String message;

	public LabelAlreadyExistException(String message) {
		super();
		this.message = message;
	}
}
