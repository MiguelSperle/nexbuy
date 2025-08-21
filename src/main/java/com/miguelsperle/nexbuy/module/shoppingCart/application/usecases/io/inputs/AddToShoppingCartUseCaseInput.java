package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

import java.math.BigDecimal;

public record AddToShoppingCartUseCaseInput(
        String productId,
        BigDecimal unitPrice,
        Integer quantity
) {
    public static AddToShoppingCartUseCaseInput with(
            String productId,
            BigDecimal unitPrice,
            Integer quantity
    ) {
        return new AddToShoppingCartUseCaseInput(
                productId,
                unitPrice,
                quantity
        );
    }
}
