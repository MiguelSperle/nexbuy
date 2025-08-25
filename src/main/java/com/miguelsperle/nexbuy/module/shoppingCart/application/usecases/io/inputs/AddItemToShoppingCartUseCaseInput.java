package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

import java.math.BigDecimal;

public record AddItemToShoppingCartUseCaseInput(
        String shoppingCartId,
        String productId,
        BigDecimal unitPrice
) {
    public static AddItemToShoppingCartUseCaseInput with(
            String shoppingCartId,
            String productId,
            BigDecimal unitPrice
    ) {
        return new AddItemToShoppingCartUseCaseInput(
                shoppingCartId,
                productId,
                unitPrice
        );
    }
}
