package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

public record DeleteShoppingCartItemUseCaseInput(
        String cartId,
        String itemId
) {
    public static DeleteShoppingCartItemUseCaseInput with(
            String cartId,
            String itemId
    ) {
        return new DeleteShoppingCartItemUseCaseInput(
                cartId,
                itemId
        );
    }
}
