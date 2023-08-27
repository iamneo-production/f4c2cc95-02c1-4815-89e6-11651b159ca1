package com.hackathon.authenticationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hackathon.authenticationservice.model.ErrorModel;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(UserNotValidException.class)
	public ResponseEntity<ErrorModel> handleUserNotFoundException(Exception ex)
	{
		ErrorModel em = new ErrorModel();
		em.setErrorCode("ERROR101");
		em.setErrorMessage("Username or password is not valid");
		return new ResponseEntity<ErrorModel>(em, HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(JWTNotValidException.class)
	public ResponseEntity<ErrorModel> handleJwtNotValidException(Exception ex)
	{
		ErrorModel em = new ErrorModel();
		em.setErrorCode("ERROR102");
		em.setErrorMessage("JWT token is not valid");
		return new ResponseEntity<ErrorModel>(em, HttpStatus.INTERNAL_SERVER_ERROR);
	
	}
	
	@ExceptionHandler(RegisterationServiceException.class)
	public ResponseEntity<ErrorModel> handleRegisterationServiceException(Exception ex)
	{
		ErrorModel em = new ErrorModel();
		em.setErrorCode("ERROR103");
		em.setErrorMessage(ex.getMessage());
		return new ResponseEntity<ErrorModel>(em, HttpStatus.INTERNAL_SERVER_ERROR);	
	}

}
