package com.example.inventory_service.controller;

import com.example.inventory_service.exception.InvalidOrderException;
import com.example.inventory_service.exception.InvalidStockException;
import com.example.inventory_service.exception.StockExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidStockException.class)
    public ResponseEntity<?> handleInvalidStockException(InvalidStockException exception, HttpServletRequest request){
        StockExceptionResponse response = new StockExceptionResponse(
                LocalDateTime.now(), exception.getMessage(),
                "stock not found", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<StockExceptionResponse>  handleInvalidStockException(
            InvalidOrderException exception,
            HttpServletRequest request){
        StockExceptionResponse response = new StockExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                "order not found ",
                request.getRequestURI());

        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

}
