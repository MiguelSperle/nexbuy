package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

public record GetShoppingCartUseCaseInput(
        String shoppingCartId
) {
    public static GetShoppingCartUseCaseInput with(String shoppingCartId) {
        return new GetShoppingCartUseCaseInput(shoppingCartId);
    }
}
