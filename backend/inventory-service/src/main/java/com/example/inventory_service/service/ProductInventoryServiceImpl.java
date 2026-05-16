package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryDto;
import com.example.inventory_service.dto.ReserveStockDto;
import com.example.inventory_service.entity.InventoryEntity;
import com.example.inventory_service.entity.ReserveStockEntity;
import com.example.inventory_service.enums.Status;
import com.example.inventory_service.exception.InvalidOrderException;
import com.example.inventory_service.exception.InvalidStockException;
import com.example.inventory_service.mapper.ProductInventoryMapper;
import com.example.inventory_service.repository.ProductInventoryRepository;
import com.example.inventory_service.repository.ReserveStockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

    @Service
    @RequiredArgsConstructor
    public class ProductInventoryServiceImpl implements ProductInventoryService{

        private final ProductInventoryRepository inventoryRepository;

        private final ProductInventoryMapper inventoryMapper;

        private final ReserveStockRepository reserveStockRepository;


        @Override
        public InventoryDto addStock(InventoryDto inventoryDto) {
            System.out.println(inventoryDto);
            InventoryEntity stockDetails = inventoryMapper.toInventoryEntity(inventoryDto);
            return inventoryMapper.toInventoryDto(inventoryRepository.save(stockDetails));
        }

        @Override
        public InventoryDto getStock(String id) {
            InventoryEntity stockDetails = inventoryRepository.findByProductId(id).orElseThrow(
                    ()-> new InvalidStockException("Stock details not found "));

            return inventoryMapper.toInventoryDto(stockDetails);
        }

        @Override
        @Transactional
        //When user clicks place order
    public InventoryDto reserveStock(ReserveStockDto reserveStockRequest) {

        InventoryEntity stock = inventoryRepository.findByProductId(reserveStockRequest.getProductId()).orElseThrow(
                ()-> new InvalidStockException("Stock details not found "));
        if(stock.getAvailableQuantity()<reserveStockRequest.getProductQuantity()){
            throw new InvalidStockException(" Insufficient stock ! ");
        }

        //Set reservation of stock
        ReserveStockEntity reserveStock = new ReserveStockEntity();
        reserveStock.setOrderId(reserveStockRequest.getOrderId());
        reserveStock.setProductId(reserveStockRequest.getProductId());
        reserveStock.setProductQuantity(reserveStockRequest.getProductQuantity());
        reserveStock.setStatus(Status.RESERVED);
        reserveStock.setReservedAt(LocalDateTime.now());
        reserveStock.setExpiredAt(LocalDateTime.now().plusSeconds(20));
        reserveStockRepository.save(reserveStock);

        //Set Inventory
        stock.setReservedQuantity(stock.getReservedQuantity() +  reserveStockRequest.getProductQuantity());
        stock.setAvailableQuantity(stock.getAvailableQuantity() - reserveStockRequest.getProductQuantity());
        return inventoryMapper.toInventoryDto(inventoryRepository.save(stock));

    }

    @Override
    @Transactional
    public void releaseStock(String id) {

        System.out.println("In Release Stock");
        ReserveStockEntity reservedStock = reserveStockRepository.findByProductId(id).orElseThrow(
                ()->new InvalidStockException("no reserve stock"));

        InventoryEntity stock = inventoryRepository.findByProductId(id).orElseThrow(
                ()-> new InvalidStockException("Stock details not found "));

        if(reservedStock.getStatus() == Status.RELEASED ){
            return;
        }

        stock.setReservedQuantity(stock.getReservedQuantity() - reservedStock.getProductQuantity());
        stock.setAvailableQuantity(stock.getAvailableQuantity() + reservedStock.getProductQuantity());

        reservedStock.setStatus(Status.RELEASED);
        reservedStock.setReleasedAt(LocalDateTime.now());
        reserveStockRepository.save(reservedStock);
        inventoryRepository.save(stock);

    }

    @Override
    public void deleteStock(String id) {
        InventoryEntity stock = inventoryRepository.findByProductId(id).orElseThrow(
                ()->new InvalidStockException("stock not found"));
        inventoryRepository.delete(stock);

    }

    @Override
    @Transactional
    public void confirmReservation(long orderId) {

        ReserveStockEntity reservedStock = reserveStockRepository.findByOrderId(orderId).orElseThrow(
                ()-> new InvalidOrderException("order not found"));

        if(reservedStock.getStatus() != Status.RESERVED){
            throw new IllegalStateException("Order not reserved ");
        }

        InventoryEntity stock = inventoryRepository.findByProductId(reservedStock.getProductId()).orElseThrow(
                ()-> new InvalidStockException("stock not found "));

        stock.setReservedQuantity(stock.getReservedQuantity() - reservedStock.getProductQuantity());

        reservedStock.setConfirmedAt(LocalDateTime.now());
        reservedStock.setStatus(Status.CONFIRMED);

        reserveStockRepository.save(reservedStock);
        inventoryRepository.save(stock);

    }
}
