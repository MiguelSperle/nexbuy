package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

public record DeleteAllShoppingCartItemsUseCaseInput(
        String shoppingCartId
) {
    public static DeleteAllShoppingCartItemsUseCaseInput with(String shoppingCartId) {
        return new DeleteAllShoppingCartItemsUseCaseInput(shoppingCartId);
    }
}
