package com.example.inventory_service.mapper;

import com.example.inventory_service.dto.InventoryDto;
import com.example.inventory_service.entity.InventoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductInventoryMapper {

    InventoryEntity toInventoryEntity(InventoryDto inventoryDto);

    InventoryDto toInventoryDto(InventoryEntity inventoryEntity);

}
