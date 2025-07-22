package com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

public record GetActiveProductUseCaseOutput(
        Product product
) {
    public static GetActiveProductUseCaseOutput from(Product product) {
        return new GetActiveProductUseCaseOutput(product);
    }
}
