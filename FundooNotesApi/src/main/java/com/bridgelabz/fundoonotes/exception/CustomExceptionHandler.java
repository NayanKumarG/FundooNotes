/**
 * @author Nayan Kumar G
 * purpose :Custom exception handler file
 * date    :11-03-2020
 */
package com.bridgelabz.fundoonotes.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoonotes.response.ErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException userException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(userException.getMessage());
        errorResponse.setStatusCode(userException.getStatusCode());;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errorResponse.getMessage(), errorResponse.getStatusCode()));
    
	}

	@ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<ErrorResponse> handleUserAlreadyExistException(UserAlreadyExistException userException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(userException.getMessage());
        errorResponse.setStatusCode(userException.getStatusCode());
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ErrorResponse(errorResponse.getMessage(), errorResponse.getStatusCode()));
    
	}
	
	@ExceptionHandler(NoteNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleNoteNotFoundException(NoteNotFoundException noteException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(noteException.getMessage());
        errorResponse.setStatusCode(noteException.getStatusCode());;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errorResponse.getMessage(), errorResponse.getStatusCode()));
    
	}
	
	@ExceptionHandler(LabelNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleLabelNotFoundException(LabelNotFoundException labelException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(labelException.getMessage());
        errorResponse.setStatusCode(labelException.getStatusCode());;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errorResponse.getMessage(), errorResponse.getStatusCode()));
    
	}
	
	@ExceptionHandler(LabelAlreadyExistException.class)
    public final ResponseEntity<ErrorResponse> handleLabelAlreadyExistException(LabelAlreadyExistException labelException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(labelException.getMessage());
        errorResponse.setStatusCode(labelException.getStatusCode());
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ErrorResponse(errorResponse.getMessage(), errorResponse.getStatusCode()));
    
	}
	
	@ExceptionHandler(InvalidUserCredentialException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidUserCredentialException(InvalidUserCredentialException userException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(userException.getMessage());
        errorResponse.setStatusCode(userException.getStatusCode());
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ErrorResponse(errorResponse.getMessage(), errorResponse.getStatusCode()));
    
	}
	
	@ExceptionHandler(InvalidTokenException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException userException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(userException.getMessage());
        errorResponse.setStatusCode(userException.getStatusCode());
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ErrorResponse(errorResponse.getMessage(), errorResponse.getStatusCode()));
    
	}
	
	@ExceptionHandler(MailNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleMailNotFoundException(MailNotFoundException mailException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(mailException.getMessage());
        errorResponse.setStatusCode(mailException.getStatusCode());
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ErrorResponse(errorResponse.getMessage(), errorResponse.getStatusCode()));
    
	}
}
