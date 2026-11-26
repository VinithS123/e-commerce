package com.example.order_service.service;

import com.example.order_service.dto.InventoryDto;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.OrderRequestDto;
import com.example.order_service.dto.ReserveStockDto;
import com.example.order_service.entity.OrderEntity;
import com.example.order_service.enums.OrderStatus;
import com.example.order_service.exception.InvalidOrderException;
import com.example.order_service.exception.OrderNotFoundException;
import com.example.order_service.feign.InventoryServiceClient;
import com.example.order_service.feign.UserServiceClient;
import com.example.order_service.mapper.OrderMapper;
import com.example.order_service.respository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    final InventoryServiceClient inventoryServiceClient;

    final OrderMapper orderMapper;

    final OrderRepository orderRepository;

    final NotificationProducer notificationProducer;

    @Override
    public OrderDto placeOrder(OrderRequestDto orderRequestDto) {

        OrderEntity order = orderMapper.toOrderEntity(orderRequestDto);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        OrderEntity savedOrder = orderRepository.save(order);

        ReserveStockDto reserveStock = orderMapper.toReserveStockDto(savedOrder);
        InventoryDto inventoryDto = inventoryServiceClient.reserveStock(reserveStock);

        if(inventoryDto.getReservedQuantity()>0){
            savedOrder.setOrderStatus(OrderStatus.RESERVED);
            OrderDto orderDto =  orderMapper.toOrderDto(orderRepository.save(savedOrder));
            notificationProducer.send(orderRequestDto.getUserId());
            return orderDto;
        }

        savedOrder.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(savedOrder);
        throw new InvalidOrderException("invalid order quantity");
    }

    @Override
    public OrderDto cancelOrder(long orderId) {

        OrderEntity order = orderRepository.findByOrderId(orderId).orElseThrow(
                ()-> new OrderNotFoundException(" Order not Found"));

        if(order.getOrderStatus()==OrderStatus.PAID){
            throw new IllegalStateException("Paid orders cannot be cancelled ");
        }

        inventoryServiceClient.releaseStock(order.getProductId());
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return orderMapper.toOrderDto(order);
    }

    @Override
    public OrderDto updatePaid(long orderId) {
        OrderEntity order = orderRepository.findByOrderId(orderId).orElseThrow(
                ()-> new OrderNotFoundException(" Order Not Found"));
        if(order.getOrderStatus() != OrderStatus.RESERVED){
            throw  new IllegalStateException("Action not supported");
        }
        order.setOrderStatus(OrderStatus.PAID);
        order.setUpdatedAt(LocalDateTime.now());
        inventoryServiceClient.confirmReservation(orderId);
        return orderMapper.toOrderDto(order);
    }
}
