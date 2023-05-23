package com.matteo.ToDo_app.Exceptions;

public class UserDoesNotOwnTask extends Exception{
    public UserDoesNotOwnTask(String message) {
        super(message);
    }
}
