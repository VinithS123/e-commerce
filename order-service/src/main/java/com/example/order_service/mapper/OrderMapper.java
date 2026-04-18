package com.example.order_service.mapper;


import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.OrderRequestDto;
import com.example.order_service.dto.ReserveStockDto;
import com.example.order_service.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    ReserveStockDto toReserveStockDto(OrderEntity orderDto);

    OrderEntity toOrderEntity(OrderRequestDto orderDto);

    OrderDto toOrderDto(OrderEntity orderEntity);

}
