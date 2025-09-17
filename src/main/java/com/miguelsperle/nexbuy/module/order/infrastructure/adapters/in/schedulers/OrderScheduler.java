package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.schedulers;

import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderRepository;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.domain.enums.OrderStatus;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderScheduler {
    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 */5 * * * *")
    public void changeOrderStatusToSent() {
        this.changeOrderStatus(OrderStatus.PAID, OrderStatus.SENT, 5);
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void changeOrderStatusToDelivered() {
        this.changeOrderStatus(OrderStatus.SENT, OrderStatus.DELIVERED, 10);
    }

    private void changeOrderStatus(OrderStatus from, OrderStatus to, int minutes) {
        final LocalDateTime time = TimeUtils.now();
        final List<Order> orders = this.getAllOrdersByStatus(from);

        for (Order order : orders) {
            if (order.getCreatedAt().isBefore(time.minusMinutes(minutes))) {
                this.saveOrder(order.withOrderStatus(to));
            }
        }
    }

    private List<Order> getAllOrdersByStatus(OrderStatus orderStatus) {
        return this.orderRepository.findAllOrdersByStatus(orderStatus);
    }

    private void saveOrder(Order order) {
        this.orderRepository.save(order);
    }
}
