package com.example.inventory_service.dto;

import lombok.Data;

@Data
public class ReserveStockDto {

    private long orderId;

    private String productId;

    private long productQuantity;

}
