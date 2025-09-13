package com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;

public record GetOrdersUseCaseOutput(
        Pagination<Order> paginatedOrders
) {
    public static GetOrdersUseCaseOutput from(Pagination<Order> paginatedOrders) {
        return new GetOrdersUseCaseOutput(paginatedOrders);
    }
}
