package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

import java.math.BigDecimal;

public record AddItemToShoppingCartUseCaseInput(
        String productId,
        BigDecimal unitPrice,
        Integer quantity
) {
    public static AddItemToShoppingCartUseCaseInput with(
            String productId,
            BigDecimal unitPrice,
            Integer quantity
    ) {
        return new AddItemToShoppingCartUseCaseInput(
                productId,
                unitPrice,
                quantity
        );
    }
}
