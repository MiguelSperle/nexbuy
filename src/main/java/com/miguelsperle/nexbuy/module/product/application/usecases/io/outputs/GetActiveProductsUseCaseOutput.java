package com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public record GetActiveProductsUseCaseOutput(
        Pagination<Product> paginatedProducts
) {
    public static GetActiveProductsUseCaseOutput from(Pagination<Product> paginatedProducts) {
        return new GetActiveProductsUseCaseOutput(paginatedProducts);
    }
}
