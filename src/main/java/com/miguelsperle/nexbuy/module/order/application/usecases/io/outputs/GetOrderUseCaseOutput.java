package com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderDelivery;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderItem;

import java.util.List;

public record GetOrderUseCaseOutput(
        Order order,
        List<OrderItem> orderItems,
        OrderDelivery orderDelivery
) {
    public static GetOrderUseCaseOutput from(
            Order order,
            List<OrderItem> orderItems,
            OrderDelivery orderDelivery
    ) {
        return new GetOrderUseCaseOutput(
                order,
                orderItems,
                orderDelivery
        );
    }
}
