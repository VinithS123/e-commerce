package com.example.product_service.controller;

import com.example.product_service.dto.ExceptionResponseDto;
import com.example.product_service.dto.ImageUploadResponseDto;
import com.example.product_service.exception.ImageUploadException;
import com.example.product_service.exception.InvalidPatchException;
import com.example.product_service.exception.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.ExceptionHandlerMappingInfo;

import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleProductNotFoundException(
            ProductNotFoundException exception, HttpServletRequest request){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(LocalDateTime.now(),exception.getMessage(),
                "product not found",request.getRequestURI());

        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<ExceptionResponseDto> handleImageUploadException(
            ImageUploadException exception, HttpServletRequest request){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(LocalDateTime.now(),exception.getMessage(),
                "image not uploaded", request.getRequestURI());
                return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InvalidPatchException.class)
    public ResponseEntity<ExceptionResponseDto> handleInvalidPatchException(
            InvalidPatchException exception, HttpServletRequest request){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(LocalDateTime.now(), exception.getMessage(),
        "Invalid Patch ", request.getRequestURI());
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
    }


}
