package com.covid19.covid19api.controller;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler( ConstraintViolationException.class )
	public ResponseEntity<Object> handleConstraintViolation( ConstraintViolationException ex, WebRequest request ) {
		return new ResponseEntity<>( ex.getMessage(), HttpStatus.BAD_REQUEST );
	}
}
