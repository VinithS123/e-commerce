package com.example.order_service.feign;

import com.example.order_service.dto.InventoryDto;
import com.example.order_service.dto.ReserveStockDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventory-service", path="api/v1/inventory")
public interface InventoryServiceClient {

    @PostMapping("/reserve-stock")
    InventoryDto reserveStock(ReserveStockDto reserveStockDto);

    @PostMapping("/release-stock/{id}")
    void releaseStock(String id);

    @PostMapping("/confirm-order/{id}")
    void confirmReservation(long id);

}
