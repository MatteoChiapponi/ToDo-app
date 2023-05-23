package com.matteo.ToDo_app.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<?> handleBadRequestExeption(BadRequestException badReq){
        return ResponseEntity.badRequest().body(badReq.getMessage());
    }

    @ExceptionHandler(EntityNotFoudException.class)
    protected ResponseEntity<?> handleEntityNotFoundException(EntityNotFoudException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(SqlViolationException.class)
    protected ResponseEntity<?> handleSqlViolationException(SqlViolationException sqlViolationException){
        return ResponseEntity.badRequest().body(sqlViolationException.getMessage());
    }

    @ExceptionHandler(UserDoesNotOwnTask.class)
    protected ResponseEntity<?> handleUserDoesNotOwnTaskException(UserDoesNotOwnTask userDoesNotOwnTask){
        return ResponseEntity.badRequest().body(userDoesNotOwnTask.getMessage());
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,Object> clientErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
                clientErrors.put(fieldError.getField(),fieldError.getDefaultMessage());
                System.out.println(fieldError.getObjectName());
        });
        return ResponseEntity.badRequest().body(clientErrors);
    }
}
