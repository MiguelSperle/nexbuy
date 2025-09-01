package com.miguelsperle.nexbuy.module.order.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.order.domain.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll();
    Optional<Order> findById(String id);
    Order save(Order order);
    void deleteById(String id);
}
