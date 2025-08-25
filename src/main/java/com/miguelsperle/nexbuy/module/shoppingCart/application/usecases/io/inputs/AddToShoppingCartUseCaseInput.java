package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

import java.math.BigDecimal;

public record AddToShoppingCartUseCaseInput(
        String shoppingCartId,
        String productId,
        BigDecimal unitPrice
) {
    public static AddToShoppingCartUseCaseInput with(
            String shoppingCartId,
            String productId,
            BigDecimal unitPrice
    ) {
        return new AddToShoppingCartUseCaseInput(
                shoppingCartId,
                productId,
                unitPrice
        );
    }
}
