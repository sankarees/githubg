package com.githubg.actions.exceptions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GithubExceptionHandler {
	@ExceptionHandler({ DataNotFound.class })
	public ResponseEntity<Object> handleDataNotFoundException(DataNotFound exception, WebRequest request) {
		return new ResponseEntity<>(new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false)),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ UnAuthorizedException.class })
	public ResponseEntity<Object> handleUnAuthorizedException(UnAuthorizedException exception, WebRequest request) {
		return new ResponseEntity<>(new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false)),
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({ InvalidInputException.class })
	public ResponseEntity<Object> handleInvalidRequestException(InvalidInputException exception, WebRequest request) {
		return new ResponseEntity<>(new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException exception, WebRequest request) {
		return new ResponseEntity<>(new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}
}