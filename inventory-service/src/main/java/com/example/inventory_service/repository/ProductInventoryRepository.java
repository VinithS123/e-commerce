package com.example.inventory_service.repository;

import com.example.inventory_service.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductInventoryRepository extends JpaRepository<InventoryEntity,Long> {

    Optional<InventoryEntity> findByProductId(String id);

}
