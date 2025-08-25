package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

public record DeleteShoppingCartItemUseCaseInput(
        String shoppingCartId,
        String shoppingCartItemId
) {
    public static DeleteShoppingCartItemUseCaseInput with(
            String shoppingCartId,
            String shoppingCartItemId
    ) {
        return new DeleteShoppingCartItemUseCaseInput(
                shoppingCartId,
                shoppingCartItemId
        );
    }
}
