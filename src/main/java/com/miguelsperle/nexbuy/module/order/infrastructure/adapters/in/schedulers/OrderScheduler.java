package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.schedulers;

import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderRepository;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.domain.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderScheduler {
    private final OrderRepository orderRepository;

    // Delivery simulation

    @Scheduled(cron = "0 0 6 * * *") // Six in the morning
    public void changeOrderStatusToSent() {
        this.updateOrderStatus(OrderStatus.PAID, OrderStatus.SENT);
    }

    @Scheduled(cron = "0 0 17 * * *") // 5 p.m
    public void changeOrderStatusToDelivered() {
        this.updateOrderStatus(OrderStatus.SENT, OrderStatus.DELIVERED);
    }

    private void updateOrderStatus(OrderStatus from, OrderStatus to) {
        final List<Order> orders = this.getAllOrdersByStatus(from);

        for (Order order : orders) {
            final Order updatedOrder = order.withOrderStatus(to);
            this.save(updatedOrder);
        }
    }

    private List<Order> getAllOrdersByStatus(OrderStatus orderStatus) {
        return this.orderRepository.findAllOrdersByStatus(orderStatus);
    }

    private void save(Order order) {
        this.orderRepository.save(order);
    }
}
