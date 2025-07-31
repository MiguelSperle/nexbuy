package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;

public record GetInventoryMovementsUseCaseInput(
        String sku,
        SearchQuery searchQuery
) {
    public static GetInventoryMovementsUseCaseInput with(String sku, SearchQuery searchQuery) {
        return new GetInventoryMovementsUseCaseInput(sku, searchQuery);
    }
}
