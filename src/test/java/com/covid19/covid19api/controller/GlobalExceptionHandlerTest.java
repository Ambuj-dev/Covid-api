package com.covid19.covid19api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@ExtendWith( MockitoExtension.class )
class GlobalExceptionHandlerTest {

	@Mock
	ServletWebRequest servletWebRequest;

	@InjectMocks
	private GlobalExceptionHandler target;

	@Test
	void handleConstraintViolation() {
		// Given
		Set<ConstraintViolation<?>> constraintViolations = new HashSet<ConstraintViolation<?>>();
		ConstraintViolationException exception = new ConstraintViolationException( "threshold must not be null", constraintViolations );

		// When
		ResponseEntity<Object> response = target.handleConstraintViolation( exception, servletWebRequest );

		// Then
		assertEquals( 400, response.getStatusCodeValue() );
		assertEquals( "threshold must not be null", response.getBody() );
	}
}