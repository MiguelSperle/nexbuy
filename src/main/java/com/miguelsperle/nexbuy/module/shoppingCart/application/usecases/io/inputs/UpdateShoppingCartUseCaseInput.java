package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

public record UpdateShoppingCartUseCaseInput(
        String shoppingCartId,
        String shoppingCartItemId,
        Integer quantity
) {
    public static UpdateShoppingCartUseCaseInput with(
            String shoppingCartId,
            String shoppingCartItemId,
            Integer quantity
    ) {
        return new UpdateShoppingCartUseCaseInput(
                shoppingCartId,
                shoppingCartItemId,
                quantity
        );
    }
}
