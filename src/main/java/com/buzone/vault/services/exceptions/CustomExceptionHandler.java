package com.buzone.vault.services.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.buzone.vault.services.application.exceptions.ErrorException;
import com.buzone.vault.services.application.exceptions.InvalidException;
import com.buzone.vault.services.application.exceptions.NotFoundException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException
                        (NotFoundException ex, WebRequest request) 
    {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getCode(), "NOT_FOUND", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(InvalidException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException
                        (InvalidException ex, WebRequest request) 
    {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getCode(), "BAD_REQUEST", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(ErrorException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException
                        (ErrorException ex, WebRequest request)
    {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getCode(),"BAD_REQUEST", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
