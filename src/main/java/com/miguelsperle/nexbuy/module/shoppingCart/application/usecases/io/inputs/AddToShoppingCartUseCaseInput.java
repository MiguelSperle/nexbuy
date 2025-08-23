package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

import java.math.BigDecimal;

public record AddToShoppingCartUseCaseInput(
        String productId,
        BigDecimal unitPrice
) {
    public static AddToShoppingCartUseCaseInput with(
            String productId,
            BigDecimal unitPrice
    ) {
        return new AddToShoppingCartUseCaseInput(
                productId,
                unitPrice
        );
    }
}
