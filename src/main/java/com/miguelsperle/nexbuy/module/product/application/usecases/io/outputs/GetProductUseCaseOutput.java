package com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public record GetProductUseCaseOutput(
        Product product
) {
    public static GetProductUseCaseOutput from(Product product) {
        return new GetProductUseCaseOutput(product);
    }
}
