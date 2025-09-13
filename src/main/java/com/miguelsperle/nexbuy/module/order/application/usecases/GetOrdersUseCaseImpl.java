package com.miguelsperle.nexbuy.module.order.application.usecases;

import com.miguelsperle.nexbuy.module.order.application.ports.in.usecases.GetOrdersUseCase;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderRepository;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.GetOrdersUseCaseInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs.GetOrdersUseCaseOutput;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

public class GetOrdersUseCaseImpl implements GetOrdersUseCase {
    private final OrderRepository orderRepository;
    private final SecurityContextService securityContextService;

    public GetOrdersUseCaseImpl(
            OrderRepository orderRepository,
            SecurityContextService securityContextService
    ) {
        this.orderRepository = orderRepository;
        this.securityContextService = securityContextService;
    }

    @Override
    public GetOrdersUseCaseOutput execute(GetOrdersUseCaseInput getOrdersUseCaseInput) {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final Pagination<Order> paginatedOrders = this.getAllPaginatedOrders(getOrdersUseCaseInput.searchQuery(), authenticatedUserId);

        return GetOrdersUseCaseOutput.from(paginatedOrders);
    }

    private Pagination<Order> getAllPaginatedOrders(SearchQuery searchQuery, String userId) {
        return this.orderRepository.findAllPaginatedByUserId(searchQuery, userId);
    }

    private String getAuthenticatedUserId() {
        return this.securityContextService.getAuthenticatedUserId();
    }
}
