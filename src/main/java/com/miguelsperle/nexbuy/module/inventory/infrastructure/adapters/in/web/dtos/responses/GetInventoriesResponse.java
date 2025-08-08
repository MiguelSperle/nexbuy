package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoriesUseCaseOutput;

public record GetInventoriesResponse(
        String id,
        String productId,
        String sku,
        Integer quantity
) {
    public static Pagination<GetInventoriesResponse> from(GetInventoriesUseCaseOutput getInventoriesUseCaseOutput) {
        return getInventoriesUseCaseOutput.paginatedInventories().map(paginatedInventory -> new GetInventoriesResponse(
                paginatedInventory.getId(),
                paginatedInventory.getProductId(),
                paginatedInventory.getSku(),
                paginatedInventory.getQuantity()
        ));
    }
}
