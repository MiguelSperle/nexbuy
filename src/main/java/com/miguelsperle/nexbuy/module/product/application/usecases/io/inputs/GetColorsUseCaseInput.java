package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

public record GetColorsUseCaseInput(
        SearchQuery searchQuery
) {
    public static GetColorsUseCaseInput with(SearchQuery searchQuery) {
        return new GetColorsUseCaseInput(searchQuery);
    }
}
