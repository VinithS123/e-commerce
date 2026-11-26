package com.example.user_service.exception;

public class UserNotFoundExcepion extends RuntimeException {

    public UserNotFoundExcepion(String message) {
        super(message);
    }
}
