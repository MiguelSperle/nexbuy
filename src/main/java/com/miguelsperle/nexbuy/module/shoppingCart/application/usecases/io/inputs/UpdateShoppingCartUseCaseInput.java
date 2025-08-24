package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

public record UpdateShoppingCartUseCaseInput(
        String cartId,
        String itemId,
        Integer quantity,
        String action
) {
    public static UpdateShoppingCartUseCaseInput with(
            String cartId,
            String itemId,
            Integer quantity,
            String action
    ) {
        return new UpdateShoppingCartUseCaseInput(
                cartId,
                itemId,
                quantity,
                action
        );
    }
}
