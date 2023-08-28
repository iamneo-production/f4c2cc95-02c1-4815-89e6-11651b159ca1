package com.hackathon.adminservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hackathon.adminservice.dto.ErrorModel;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(QuestionIdNotValidException.class)
	public ResponseEntity<ErrorModel> handleQuestionNotFoundException(Exception ex) {
		ErrorModel em = new ErrorModel();
		em.setErrorCode("ERR301");
		em.setErrorMessage("Question Id is not present");
		return new ResponseEntity<>(em, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(LimitIsGreaterThanQuestionAvailabilityException.class)
	public ResponseEntity<ErrorModel> handleLimitException(Exception ex) {
		ErrorModel em = new ErrorModel();
		em.setErrorCode("ERR302");
		em.setErrorMessage("Limit is Greater than availability.Available Questions are " + ex.getMessage() + " only");
		return new ResponseEntity<>(em, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidCategoryException.class)
	public ResponseEntity<ErrorModel> handleInvalidCategoryException(Exception ex) {
		ErrorModel em = new ErrorModel();
		em.setErrorCode("ERR303");
		em.setErrorMessage("Invalid category");
		return new ResponseEntity<>(em, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidLevelException.class)
	public ResponseEntity<ErrorModel> handleInvalidLevelException(Exception ex) {
		ErrorModel em = new ErrorModel();
		em.setErrorCode("ERR304");
		em.setErrorMessage("Invalid Level");
		return new ResponseEntity<>(em, HttpStatus.NOT_FOUND);
	}
}
