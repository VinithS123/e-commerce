package com.example.order_service.dto;

import com.example.order_service.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {

    private long orderId;

    private String productId;

    private long productQuantity;

    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

}
