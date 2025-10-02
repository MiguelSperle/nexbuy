package com.miguelsperle.nexbuy.module.order.application.usecases;

import com.miguelsperle.nexbuy.module.order.application.ports.in.usecases.GetOrderUseCase;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderDeliveryRepository;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderItemRepository;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderRepository;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.GetOrderUseCaseInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs.GetOrderUseCaseOutput;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderDelivery;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderItem;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

import java.util.List;

public class GetOrderUseCaseImpl implements GetOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderDeliveryRepository orderDeliveryRepository;

    public GetOrderUseCaseImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            OrderDeliveryRepository orderDeliveryRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderDeliveryRepository = orderDeliveryRepository;
    }

    @Override
    public GetOrderUseCaseOutput execute(GetOrderUseCaseInput getOrderUseCaseInput) {
        final Order order = this.getOrderById(getOrderUseCaseInput.orderId());

        final List<OrderItem> orderItems = this.getOrderItemsByOrderId(order.getId());

        final OrderDelivery orderDelivery = this.getOrderDeliveryByOrderId(order.getId());

        return GetOrderUseCaseOutput.from(order, orderItems, orderDelivery);
    }

    private Order getOrderById(String orderId) {
        return this.orderRepository.findById(orderId)
                .orElseThrow(() -> NotFoundException.with("Order not found"));
    }

    private List<OrderItem> getOrderItemsByOrderId(String orderId) {
        return this.orderItemRepository.findAllByOrderId(orderId);
    }

    private OrderDelivery getOrderDeliveryByOrderId(String orderId) {
        return this.orderDeliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> NotFoundException.with("Order delivery not found"));
    }
}
