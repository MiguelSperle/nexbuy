package com.miguelsperle.nexbuy.module.product.application.dtos.inputs;

import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;

public record GetProductsUseCaseInput(
        SearchQuery searchQuery
) {
    public static GetProductsUseCaseInput with(SearchQuery searchQuery) {
        return new GetProductsUseCaseInput(searchQuery);
    }
}
