package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;

public record GetInventoryMovementsUseCaseInput(
        SearchQuery searchQuery
) {
    public static GetInventoryMovementsUseCaseInput with(SearchQuery searchQuery) {
        return new GetInventoryMovementsUseCaseInput(searchQuery);
    }
}
