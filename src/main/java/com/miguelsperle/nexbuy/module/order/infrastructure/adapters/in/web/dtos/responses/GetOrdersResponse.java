package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs.GetOrdersUseCaseOutput;
import com.miguelsperle.nexbuy.module.order.domain.enums.OrderStatus;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetOrdersResponse(
        String id,
        OrderStatus status,
        String code,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {
    public static Pagination<GetOrdersResponse> from(GetOrdersUseCaseOutput getOrdersUseCaseOutput) {
        return getOrdersUseCaseOutput.paginatedOrders().map(paginatedOrder -> new GetOrdersResponse(
                paginatedOrder.getId(),
                paginatedOrder.getOrderStatus(),
                paginatedOrder.getCode(),
                paginatedOrder.getTotalAmount(),
                paginatedOrder.getCreatedAt()
        ));
    }
}
