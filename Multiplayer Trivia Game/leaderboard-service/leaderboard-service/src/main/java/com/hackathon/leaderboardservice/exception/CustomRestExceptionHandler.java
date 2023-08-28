package com.hackathon.leaderboardservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hackathon.leaderboardservice.model.ErrorModel;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(NoGamePlayedException.class)
	public ResponseEntity<ErrorModel> handleNoGamePlayedException(Exception ex)
	{
		ErrorModel em = new ErrorModel();
		em.setErrorCode("ERROR501");
		em.setErrorMessage("Username has not played any game");
		return new ResponseEntity<ErrorModel>(em, HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(GameplayMechanicsServiceException.class)
	public ResponseEntity<ErrorModel> handleGameplayMechanicsServiceException(Exception ex)
	{
		ErrorModel em = new ErrorModel();
		em.setErrorCode("ERROR502");
		em.setErrorMessage(ex.getMessage());
		return new ResponseEntity<ErrorModel>(em, HttpStatus.FORBIDDEN);	
	}
	

}
