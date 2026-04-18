package com.example.inventory_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StockExceptionResponse {

    private LocalDateTime timeStamp;

    private String message;

    private String details;

    private String path;
}
