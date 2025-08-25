package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

public record UpdateShoppingCartUseCaseInput(
        String shoppingCartId,
        String shoppingCartItemId,
        Integer quantity,
        String action
) {
    public static UpdateShoppingCartUseCaseInput with(
            String shoppingCartId,
            String shoppingCartItemId,
            Integer quantity,
            String action
    ) {
        return new UpdateShoppingCartUseCaseInput(
                shoppingCartId,
                shoppingCartItemId,
                quantity,
                action
        );
    }
}
