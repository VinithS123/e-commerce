package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryDto;
import com.example.inventory_service.dto.ReserveStockDto;

public interface ProductInventoryService {

    InventoryDto addStock(InventoryDto inventoryDto);

    InventoryDto getStock(String id);

    InventoryDto reserveStock(ReserveStockDto reserveStockDto);

    void releaseStock(String id);

    void deleteStock(String id);

    void confirmReservation(long orderId);
}
