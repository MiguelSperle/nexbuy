package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

public record ClearShoppingCartUseCaseInput(
    String shoppingCartId
) {
    public static ClearShoppingCartUseCaseInput with(String shoppingCartId) {
        return new ClearShoppingCartUseCaseInput(shoppingCartId);
    }
}
