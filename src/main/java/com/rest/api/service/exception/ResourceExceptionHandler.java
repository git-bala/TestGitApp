package com.rest.api.service.exception;

import java.time.LocalDateTime;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		ExceptionResponse error = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ExceptionResponse> handleThrowable(AccessDeniedException e, Locale locale) {
		log.error("Access denied", e);
		ExceptionResponse error = new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage(),
				LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(DataIntegratyViolationException.class)
	public ResponseEntity<ExceptionResponse> dataIntegratyViolationException(DataIntegratyViolationException e, Locale locale) {
		log.error("Access denied", e);
		ExceptionResponse error = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
}
