package com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

public record GetBrandsUseCaseInput(
        SearchQuery searchQuery
) {
    public static GetBrandsUseCaseInput with(SearchQuery searchQuery) {
        return new GetBrandsUseCaseInput(searchQuery);
    }
}
