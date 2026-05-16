package com.example.order_service.controller;

import com.example.order_service.exception.ExceptionResponseDto;
import com.example.order_service.exception.InvalidOrderException;
import com.example.order_service.exception.OrderNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.bouncycastle.pqc.crypto.util.PQCOtherInfoGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidOrderException.class)
        public ResponseEntity<ExceptionResponseDto> handleInvalidOrderException(
            InvalidOrderException exception,
            HttpServletRequest request){

        ExceptionResponseDto exceptionResponseDto =new ExceptionResponseDto(
                LocalDateTime.now(),
                exception.getMessage(),
                "Invalid order ",
                request.getRequestURI());

        return ResponseEntity.badRequest().body(exceptionResponseDto);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleOrderNotFoundException(
            OrderNotFoundException exception,
            HttpServletRequest request){

        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                LocalDateTime.now(),
                exception.getMessage(),
                "Order Not Found",
                request.getRequestURI());

        return ResponseEntity.status(404).body(exceptionResponseDto);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionResponseDto>  handleIllegalStateException(
            IllegalStateException exception,
            HttpServletRequest request){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                LocalDateTime.now(),
                exception.getMessage(),
                "action not supported",
                request.getRequestURI());
        return ResponseEntity.status(409).body(exceptionResponseDto);
    }


}
