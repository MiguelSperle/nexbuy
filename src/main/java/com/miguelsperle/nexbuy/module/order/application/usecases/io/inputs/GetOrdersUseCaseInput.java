package com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

public record GetOrdersUseCaseInput(
        SearchQuery searchQuery
) {
    public static GetOrdersUseCaseInput with(SearchQuery searchQuery) {
        return new GetOrdersUseCaseInput(searchQuery);
    }
}
