package com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.common.domain.pagination.SearchQuery;

public record GetOrdersUseCaseInput(
        SearchQuery searchQuery
) {
    public static GetOrdersUseCaseInput with(SearchQuery searchQuery) {
        return new GetOrdersUseCaseInput(searchQuery);
    }
}
