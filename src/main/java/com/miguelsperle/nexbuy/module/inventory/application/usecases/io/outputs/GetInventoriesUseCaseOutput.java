package com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;

public record GetInventoriesUseCaseOutput(
        Pagination<Inventory> paginatedInventories
) {
    public static GetInventoriesUseCaseOutput from(Pagination<Inventory> paginatedInventories) {
        return new GetInventoriesUseCaseOutput(paginatedInventories);
    }
}
