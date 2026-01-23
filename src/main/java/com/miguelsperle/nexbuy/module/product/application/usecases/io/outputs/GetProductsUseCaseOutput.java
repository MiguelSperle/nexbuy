package com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public record GetProductsUseCaseOutput(
        Pagination<Product> paginatedProducts
) {
    public static GetProductsUseCaseOutput from(Pagination<Product> paginatedProducts) {
        return new GetProductsUseCaseOutput(paginatedProducts);
    }
}
