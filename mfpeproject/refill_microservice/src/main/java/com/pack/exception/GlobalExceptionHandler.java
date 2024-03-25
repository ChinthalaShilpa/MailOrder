package com.pack.exception;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		// Get all validation errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
		.collect(Collectors.toList());
		// Add errors to the response map
		body.put("errors", errors);
		log.info("End");
		return new ResponseEntity<>(body, headers, status);
	}
	 
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<ExceptionDetails> handleAuthorizationException(AuthorizationException ex){
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.FORBIDDEN, ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ExceptionDetails> handleMissingRequestHeaderException(MissingRequestHeaderException ex){
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(DrugDetailNotFoundException.class)
	public ResponseEntity<ExceptionDetails> handleDrugDetailNotFoundException(DrugDetailNotFoundException ex){
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DrugLocationNotFound.class)
	public ResponseEntity<ExceptionDetails> handleDrugLocationNotFound(DrugLocationNotFound ex){
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MemberSubscriptionNotFoundException.class)
	public ResponseEntity<ExceptionDetails> handleMemberSubscriptionNotFoundException(MemberSubscriptionNotFoundException ex){
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_FOUND);
	
	}
	
	@ExceptionHandler(DueIsFoundException.class)
	public ResponseEntity<ExceptionDetails> handleDueIsFoundException(DueIsFoundException ex){
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.FOUND, ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.FOUND);
	
	}
	
	@ExceptionHandler(DrugNotAvailableInLocationException.class)
	public ResponseEntity<ExceptionDetails> handleDrugNotAvailableInLocationException(DrugNotAvailableInLocationException ex){
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_FOUND);
	
	}
	
	
	
	
	
}