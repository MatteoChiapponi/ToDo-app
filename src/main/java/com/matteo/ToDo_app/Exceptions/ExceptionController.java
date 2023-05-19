package com.matteo.ToDo_app.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,Object> clientErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
                clientErrors.put(fieldError.getField(),fieldError.getDefaultMessage());
                System.out.println(fieldError.getObjectName());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(clientErrors);
    }
}
