package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

import java.math.BigDecimal;

public record AddToShoppingCartUseCaseInput(
        String cartId,
        String productId,
        BigDecimal unitPrice
) {
    public static AddToShoppingCartUseCaseInput with(
            String cartId,
            String productId,
            BigDecimal unitPrice
    ) {
        return new AddToShoppingCartUseCaseInput(
                cartId,
                productId,
                unitPrice
        );
    }
}
