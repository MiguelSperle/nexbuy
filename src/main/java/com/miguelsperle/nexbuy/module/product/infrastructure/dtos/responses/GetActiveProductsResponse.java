package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetActiveProductsUseCaseOutput;

import java.math.BigDecimal;

public record GetActiveProductsResponse(
        String id,
        String name,
        BigDecimal price
) {
    public static Pagination<GetActiveProductsResponse> from(GetActiveProductsUseCaseOutput getActiveProductsUseCaseOutput) {
        return getActiveProductsUseCaseOutput.paginatedProducts().map(paginatedProduct -> new GetActiveProductsResponse(
                paginatedProduct.getId(),
                paginatedProduct.getName(),
                paginatedProduct.getPrice()
        ));
    }
}
