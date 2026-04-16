package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.rest.dtos.res;

import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.outputs.GetShoppingCartUseCaseOutput;
import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.rest.dtos.res.complements.ShoppingCartItemComplementResponse;

import java.math.BigDecimal;
import java.util.List;

public record GetShoppingCartResponse(
        String id,
        BigDecimal totalAmount,
        List<ShoppingCartItemComplementResponse> shoppingCartItems
) {
    public static GetShoppingCartResponse from(GetShoppingCartUseCaseOutput getShoppingCartUseCaseOutput) {
        return new GetShoppingCartResponse(
                getShoppingCartUseCaseOutput.shoppingCart().getId(),
                getShoppingCartUseCaseOutput.shoppingCart().getTotalAmount(),
                ShoppingCartItemComplementResponse.from(getShoppingCartUseCaseOutput.shoppingCartItems())
        );
    }
}
