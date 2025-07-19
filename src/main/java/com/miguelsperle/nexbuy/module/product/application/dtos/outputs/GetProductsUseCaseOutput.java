package com.miguelsperle.nexbuy.module.product.application.dtos.outputs;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public record GetProductsUseCaseOutput(
        Pagination<Product> productsPaginated
) {
    public static GetProductsUseCaseOutput from(Pagination<Product> productsPaginated) {
        return new GetProductsUseCaseOutput(productsPaginated);
    }
}
