package com.example.demo.exception;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class MyCustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest req){
		ExceptionResponseDetails excecptionResponseDetails = new ExceptionResponseDetails(
				new Date(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<Object>(excecptionResponseDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest req){
		ExceptionResponseDetails excecptionResponseDetails = new ExceptionResponseDetails(
				new Date(), "user not found" , ex.getMessage());
		return new ResponseEntity<Object>(excecptionResponseDetails, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request){
		
		ExceptionResponseDetails excecptionResponseDetails = new ExceptionResponseDetails(
				new Date(), "validation failed" , ex.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<Object>(excecptionResponseDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler({UserExistsException.class, EmailIdExistsException.class})
	public ResponseEntity<Object> handleEntityFieldAlreadyExists(
			Exception ex, WebRequest request) {
	    
		ExceptionResponseDetails excecptionResponseDetails = new ExceptionResponseDetails(
				new Date(), "validation failed" , ex.getMessage());
		return new ResponseEntity<Object>(excecptionResponseDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(
			DataIntegrityViolationException ex, WebRequest request) {
	    
		ExceptionResponseDetails excecptionResponseDetails = new ExceptionResponseDetails(
				new Date(), "validation failed - sql exception data integrity" , ex.getMessage());
		return new ResponseEntity<Object>(excecptionResponseDetails, HttpStatus.BAD_REQUEST);
	}

}
