package com.example.product_service.exception;

public class InvalidPatchException extends RuntimeException{

    public InvalidPatchException(String message){
        super(message);
    }
}
