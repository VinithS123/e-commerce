package com.example.order_service.controller;

import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.OrderRequestDto;
import com.example.order_service.dto.ReserveStockDto;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(orderService.placeOrder(orderRequestDto));
    }

    @PostMapping("/orders/{orderId}")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable long orderId){
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }


}
