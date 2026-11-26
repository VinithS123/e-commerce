package com.example.user_service.controller;

import com.example.user_service.dto.ExceptionDto;
import com.example.user_service.exception.UserNotFoundExcepion;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundExcepion.class)
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(UserNotFoundExcepion excepion, HttpServletRequest request){

    ExceptionDto response = new ExceptionDto(
            LocalDateTime.now(),
            excepion.getMessage(),
            "User Not present",
            request.getRequestURI());

    return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);

    }
}
