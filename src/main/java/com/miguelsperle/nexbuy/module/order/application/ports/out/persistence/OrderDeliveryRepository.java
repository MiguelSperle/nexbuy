package com.miguelsperle.nexbuy.module.order.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.order.domain.entities.OrderDelivery;

import java.util.List;
import java.util.Optional;

public interface OrderDeliveryRepository {
    List<OrderDelivery> findAll();
    Optional<OrderDelivery> findById(String id);
    OrderDelivery save(OrderDelivery orderDelivery);
    void deleteById(String id);
    Optional<OrderDelivery> findByOrderId(String orderId);
}
