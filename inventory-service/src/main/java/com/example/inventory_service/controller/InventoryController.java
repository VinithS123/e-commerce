package com.example.inventory_service.controller;

import com.example.inventory_service.dto.InventoryDto;
import com.example.inventory_service.dto.ReserveStockDto;
import com.example.inventory_service.service.ProductInventoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final ProductInventoryServiceImpl inventoryService;

    @PostMapping("/add-stock")
    public ResponseEntity<InventoryDto> addStock(@RequestBody InventoryDto inventoryDto){
        System.out.println(inventoryDto);
        return ResponseEntity.ok(inventoryService.addStock(inventoryDto));
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<InventoryDto> getStock(@PathVariable String id){
        return ResponseEntity.ok(inventoryService.getStock(id));
    }

    @PostMapping("/reserve-stock")
    public ResponseEntity<InventoryDto> reserveStock(@RequestBody ReserveStockDto reserveStockDto){
        return ResponseEntity.ok(inventoryService.reserveStock(reserveStockDto));
    }

    @PostMapping("/release-stock/{id}")
    public ResponseEntity<Void> releaseStock(@PathVariable String id ){
        inventoryService.releaseStock(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/confirm-order/{orderID}")
    public ResponseEntity<Void> confirmReservation(@PathVariable long orderId){
        inventoryService.confirmReservation(orderId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/stock/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable String id){
        inventoryService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

}
