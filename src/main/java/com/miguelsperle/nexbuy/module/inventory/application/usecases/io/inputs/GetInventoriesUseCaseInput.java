package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

public record GetInventoriesUseCaseInput(
        SearchQuery searchQuery
) {
    public static GetInventoriesUseCaseInput with(SearchQuery searchQuery) {
        return new GetInventoriesUseCaseInput(searchQuery);
    }
}
