package com.example.inventory_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Data
public class InventoryEntity {

    @Id
    private String productId;

    private long reservedQuantity;

    private long availableQuantity;

    private long totalQuantity;

    @Version
    private long version;

}
