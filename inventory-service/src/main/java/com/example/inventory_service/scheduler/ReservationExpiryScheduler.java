package com.example.inventory_service.scheduler;

import com.example.inventory_service.entity.ReserveStockEntity;
import com.example.inventory_service.enums.Status;
import com.example.inventory_service.repository.ReserveStockRepository;
import com.example.inventory_service.service.ProductInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationExpiryScheduler {

    final ReserveStockRepository repository;

    final ProductInventoryService inventoryService;

    @Scheduled(fixedDelay = 3600000)
    public void productExpiryHandler(){

        List<ReserveStockEntity> reservedStockList = repository.findByStatus(Status.RESERVED);
            reservedStockList.forEach(
                    reserveEntity -> {

                        if (reserveEntity.getExpiredAt()!=null && LocalDateTime.now().isAfter(reserveEntity.getExpiredAt())) {
                            System.out.println(reserveEntity.getProductId());
                            inventoryService.releaseStock(reserveEntity.getProductId());
                            System.out.println(" In Scheduler");
                            System.out.println(reserveEntity);
                        }
                    });
    }

}
