package com.example.inventory_service.entity;

import com.example.inventory_service.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ReserveStockEntity {

    @Id
    private long orderId;

    private String productId;

    private long productQuantity;

    // for  database mapping
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime reservedAt;

    private LocalDateTime expiredAt;

    private LocalDateTime releasedAt;

    private LocalDateTime confirmedAt;

}
