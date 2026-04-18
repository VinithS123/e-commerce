package com.example.inventory_service.dto;

import lombok.Data;

@Data
public class InventoryDto {

    private String productId;

    private long reservedQuantity;

    private long availableQuantity;

    private long totalQuantity;

}
