package com.miguelsperle.nexbuy.module.order.application.usecases;

import com.miguelsperle.nexbuy.module.order.application.abstractions.usecases.GetOrdersUseCase;
import com.miguelsperle.nexbuy.module.order.application.abstractions.repositories.OrderRepository;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.GetOrdersUseCaseInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs.GetOrdersUseCaseOutput;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityService;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

public class GetOrdersUseCaseImpl implements GetOrdersUseCase {
    private final OrderRepository orderRepository;
    private final SecurityService securityService;

    public GetOrdersUseCaseImpl(
            OrderRepository orderRepository,
            SecurityService securityService
    ) {
        this.orderRepository = orderRepository;
        this.securityService = securityService;
    }

    @Override
    public GetOrdersUseCaseOutput execute(GetOrdersUseCaseInput getOrdersUseCaseInput) {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final Pagination<Order> paginatedOrders = this.getAllPaginatedOrdersByUserId(getOrdersUseCaseInput.searchQuery(), authenticatedUserId);

        return GetOrdersUseCaseOutput.from(paginatedOrders);
    }

    private Pagination<Order> getAllPaginatedOrdersByUserId(SearchQuery searchQuery, String userId) {
        return this.orderRepository.findAllPaginatedByUserId(searchQuery, userId);
    }

    private String getAuthenticatedUserId() {
        return this.securityService.getUserId();
    }
}
