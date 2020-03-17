/**
 * @author Nayan Kumar G
 * purpose :Error response to exception
 * date    :11-03-2020
 */
package com.bridgelabz.fundoonotes.response;


import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorResponse {



	public ErrorResponse(String message, HttpStatus statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

	public ErrorResponse() {
		// TODO Auto-generated constructor stub
	}

	private String message;
	   
	private HttpStatus statusCode;
	
	
}
