package com.example.order_service.service;

import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.OrderRequestDto;

import java.time.LocalDateTime;

public interface OrderService {

    OrderDto placeOrder(OrderRequestDto orderRequestDto);

    OrderDto cancelOrder(long orderId);

    OrderDto updatePaid(long orderId);
}
