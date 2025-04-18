package com.example.demo.CustomExceptions;

public class UserRegistrationProblemException extends RuntimeException {
    public UserRegistrationProblemException(String message) {
        super(message);
    }
}
