package com.miguelsperle.nexbuy.module.order.infrastructure.listeners;

import com.miguelsperle.nexbuy.module.order.application.abstractions.repositories.OrderRepository;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.domain.enums.OrderStatus;
import com.miguelsperle.nexbuy.module.payment.domain.events.PaymentStatusUpdatedEvent;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PaymentStatusUpdatedListener {
    private final OrderRepository orderRepository;

    private static final String PAYMENT_STATUS_UPDATED_QUEUE = "payment.status.updated.queue";

    @RabbitListener(queues = PAYMENT_STATUS_UPDATED_QUEUE)
    public void onMessage(PaymentStatusUpdatedEvent paymentStatusUpdatedEvent) {
        final Order order = this.getOrderById(paymentStatusUpdatedEvent.orderId());

        OrderStatus orderStatus;

        if (Objects.equals(paymentStatusUpdatedEvent.paymentStatus(), "APPROVED")) {
            orderStatus = OrderStatus.PAID;
        } else {
            orderStatus = OrderStatus.CANCELED;
        }

        final Order updatedOrder = order.withOrderStatus(orderStatus);

        this.saveOrder(updatedOrder);
    }

    private Order getOrderById(String id) {
        return this.orderRepository.findById(id).orElseThrow(() -> DomainException.with("Order not found", 404));
    }

    private void saveOrder(Order order) {
        this.orderRepository.save(order);
    }
}
