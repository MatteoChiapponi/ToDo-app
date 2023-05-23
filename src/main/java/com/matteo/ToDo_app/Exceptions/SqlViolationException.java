package com.matteo.ToDo_app.Exceptions;

public class SqlViolationException extends Exception{
    public SqlViolationException(String message) {
        super(message);
    }
}
