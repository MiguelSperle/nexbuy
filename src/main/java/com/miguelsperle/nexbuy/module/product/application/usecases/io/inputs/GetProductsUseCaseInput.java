package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;

public record GetProductsUseCaseInput(
        SearchQuery searchQuery
) {
    public static GetProductsUseCaseInput with(SearchQuery searchQuery) {
        return new GetProductsUseCaseInput(searchQuery);
    }
}
