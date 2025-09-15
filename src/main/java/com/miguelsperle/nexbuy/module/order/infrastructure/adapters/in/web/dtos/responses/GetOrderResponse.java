package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs.GetOrderUseCaseOutput;
import com.miguelsperle.nexbuy.module.order.domain.enums.OrderStatus;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.dtos.responses.complements.OrderDeliveryComplementResponse;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.dtos.responses.complements.OrderItemsComplementResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record GetOrderResponse(
        String id,
        OrderStatus status,
        String code,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        List<OrderItemsComplementResponse> orderItems,
        OrderDeliveryComplementResponse orderDelivery
) {
    public static GetOrderResponse from(GetOrderUseCaseOutput getOrderUseCaseOutput) {
        return new GetOrderResponse(
                getOrderUseCaseOutput.order().getId(),
                getOrderUseCaseOutput.order().getOrderStatus(),
                getOrderUseCaseOutput.order().getCode(),
                getOrderUseCaseOutput.order().getTotalAmount(),
                getOrderUseCaseOutput.order().getCreatedAt(),
                getOrderUseCaseOutput.orderItems().stream().map(orderItem -> OrderItemsComplementResponse.from(
                        orderItem.getId(),
                        orderItem.getProductId(),
                        orderItem.getQuantity(),
                        orderItem.getUnitPrice()
                )).toList(),
                OrderDeliveryComplementResponse.from(
                        getOrderUseCaseOutput.orderDelivery().getAddressLine(),
                        getOrderUseCaseOutput.orderDelivery().getAddressNumber(),
                        getOrderUseCaseOutput.orderDelivery().getZipCode(),
                        getOrderUseCaseOutput.orderDelivery().getOrderId(),
                        getOrderUseCaseOutput.orderDelivery().getCity(),
                        getOrderUseCaseOutput.orderDelivery().getUf(),
                        getOrderUseCaseOutput.orderDelivery().getComplement()
                )
        );
    }
}
