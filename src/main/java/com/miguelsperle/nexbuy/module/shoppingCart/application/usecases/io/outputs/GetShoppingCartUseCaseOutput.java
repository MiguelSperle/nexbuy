package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;

import java.util.List;

public record GetShoppingCartUseCaseOutput(
        ShoppingCart shoppingCart,
        List<ShoppingCartItem> shoppingCartItems
) {
    public static GetShoppingCartUseCaseOutput from(ShoppingCart shoppingCart, List<ShoppingCartItem> shoppingCartItems) {
        return new GetShoppingCartUseCaseOutput(shoppingCart, shoppingCartItems);
    }
}
