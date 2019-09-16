package com.stackroute.exception;

public class UserAlreadyExistException extends Exception {


     private String messge;
    public UserAlreadyExistException(String message) {
        super(message);
        this.messge = message;

    }

    public UserAlreadyExistException() {
    }
}
