package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs;

public record UpdateShoppingCartUseCaseInput(
        String itemId,
        Integer quantity,
        String action
) {
    public static UpdateShoppingCartUseCaseInput with(
            String itemId,
            Integer quantity,
            String action
    ) {
        return new UpdateShoppingCartUseCaseInput(
                itemId,
                quantity,
                action
        );
    }
}
