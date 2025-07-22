package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;

public record GetActiveProductsUseCaseInput(
        SearchQuery searchQuery
) {
    public static GetActiveProductsUseCaseInput with(SearchQuery searchQuery) {
        return new GetActiveProductsUseCaseInput(searchQuery);
    }
}
