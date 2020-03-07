/**
 * @author Nayan Kumar G
 * purpose :Class to create response
 * date    :28-02-2020
 */
package com.bridgelabz.fundoonotes.response;
import lombok.Data;


@Data
public class Response {

	private String message;
	private Object object;
	public Response(String message, Object object) {
		super();
		this.message = message;
		this.object = object;
	}

	public Response(String message) {
	
		this.message = message;
	}
}
