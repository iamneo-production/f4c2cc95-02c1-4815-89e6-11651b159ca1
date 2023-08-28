package com.hackathon.gameplaymechanicsservice.exception;

import com.hackathon.gameplaymechanicsservice.response.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalCustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidRoomIDException.class)
    public ResponseEntity<ErrorModel> handleQuestionNotFoundException(Exception ex)
    {
        ErrorModel em = new ErrorModel();
        em.setErrorCode("ERR501");
        em.setErrorMessage( ex.getMessage());
        return new ResponseEntity<>(em, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler( PlayerLimitExceedsException.class)
    public ResponseEntity<ErrorModel> handleLimitException(Exception ex)
    {
        ErrorModel em = new ErrorModel();
        em.setErrorCode("ERR502");
        em.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(em, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler( NotRoomOwnerException.class)
    public ResponseEntity<ErrorModel> handleNotRoomOwnerException(Exception ex)
    {
        ErrorModel em = new ErrorModel();
        em.setErrorCode("ERR503");
        em.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(em, HttpStatus.NOT_ACCEPTABLE);
    }


    @ExceptionHandler( SubmissionException.class)
    public ResponseEntity<ErrorModel> handleSubmitException(Exception ex)
    {
        ErrorModel em = new ErrorModel();
        em.setErrorCode("ERR504");
        em.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(em, HttpStatus.ALREADY_REPORTED);
    }



}
