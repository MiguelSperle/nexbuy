package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

public record GetCategoriesUseCaseInput(
        SearchQuery searchQuery
) {
    public static GetCategoriesUseCaseInput with(SearchQuery searchQuery) {
        return new GetCategoriesUseCaseInput(searchQuery);
    }
}
