package com.hackathon.registrationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hackathon.registrationservice.entity.ErrorModel;



@ControllerAdvice
public class CustomRestException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorModel> handleUserNotFoundException(Exception ex)
	{
		ErrorModel em = new ErrorModel();
		em.setErrorCode("ERROR901");
		em.setErrorMessage("Username already exists , try with new username");
		return new ResponseEntity<ErrorModel>(em, HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(UserNameNotValidException.class)
	public ResponseEntity<ErrorModel> handleEmailNotValidException(Exception ex)
	{
		ErrorModel em = new ErrorModel();
		em.setErrorCode("ERROR902");
		em.setErrorMessage("username is not valid, enter correct format");
		return new ResponseEntity<ErrorModel>(em, HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(EmailDuplicateException.class)
	public ResponseEntity<ErrorModel> handleEmailDuplicateException(Exception ex)
	{
		ErrorModel em = new ErrorModel();
		em.setErrorCode("ERROR903");
		em.setErrorMessage("EmailId already exists try with another EmailId");
		return new ResponseEntity<ErrorModel>(em, HttpStatus.NOT_FOUND);
	}
}

