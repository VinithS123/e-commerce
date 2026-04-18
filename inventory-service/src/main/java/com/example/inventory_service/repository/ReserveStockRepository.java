package com.example.inventory_service.repository;

import com.example.inventory_service.entity.ReserveStockEntity;
import com.example.inventory_service.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReserveStockRepository extends JpaRepository<ReserveStockEntity, Long> {

    Optional<ReserveStockEntity> findByProductId(String id);

    List<ReserveStockEntity>  findByStatus(Status status);

    Optional<ReserveStockEntity> findByOrderId(long orderId);
}
