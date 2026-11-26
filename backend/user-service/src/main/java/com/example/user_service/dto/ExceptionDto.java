package com.example.user_service.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ExceptionDto {

    private LocalDateTime time;

    private String details;

    private String message;

    private String path;

}
