package com.miguelsperle.nexbuy.module.order.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.order.domain.entities.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository {
    List<OrderItem> findAll();
    Optional<OrderItem> findById(String id);
    OrderItem save(OrderItem orderItem);
    List<OrderItem> saveAll(List<OrderItem> orderItems);
    void deleteById(String id);
    List<OrderItem> findAllByOrderId(String orderId);
}
