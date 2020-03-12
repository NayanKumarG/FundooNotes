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
	
	private String email;

	public ErrorResponse(String message, String email) {
		this.message = message;
		this.email = email;
	}


	
}
