package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

public record GetShoppingCartUseCaseInput(
        String cartId
) {
    public static GetShoppingCartUseCaseInput with(String cartId) {
        return new GetShoppingCartUseCaseInput(cartId);
    }
}
