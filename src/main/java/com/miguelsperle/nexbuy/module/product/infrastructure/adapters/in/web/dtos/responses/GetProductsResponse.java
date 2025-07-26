package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductsUseCaseOutput;

import java.math.BigDecimal;

public record GetProductsResponse(
        String id,
        String name,
        BigDecimal price
) {
    public static Pagination<GetProductsResponse> from(GetProductsUseCaseOutput getProductsUseCaseOutput) {
        return getProductsUseCaseOutput.paginatedProducts().map(paginatedProduct -> new GetProductsResponse(
                paginatedProduct.getId(),
                paginatedProduct.getName(),
                paginatedProduct.getPrice()
        ));
    }
}
