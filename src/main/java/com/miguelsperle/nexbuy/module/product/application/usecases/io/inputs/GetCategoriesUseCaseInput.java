package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.common.domain.pagination.SearchQuery;

public record GetCategoriesUseCaseInput(
        SearchQuery searchQuery
) {
    public static GetCategoriesUseCaseInput with(SearchQuery searchQuery) {
        return new GetCategoriesUseCaseInput(searchQuery);
    }
}
