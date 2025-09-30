package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

public record UpdateShoppingCartItemUseCaseInput(
        String shoppingCartId,
        String shoppingCartItemId,
        Integer quantity
) {
    public static UpdateShoppingCartItemUseCaseInput with(
            String shoppingCartId,
            String shoppingCartItemId,
            Integer quantity
    ) {
        return new UpdateShoppingCartItemUseCaseInput(
                shoppingCartId,
                shoppingCartItemId,
                quantity
        );
    }
}
