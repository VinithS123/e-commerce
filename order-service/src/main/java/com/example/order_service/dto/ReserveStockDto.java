package com.example.order_service.dto;

import lombok.Data;

@Data
public class ReserveStockDto {

    private long orderId;

    private String productId;

    private long productQuantity;

}
