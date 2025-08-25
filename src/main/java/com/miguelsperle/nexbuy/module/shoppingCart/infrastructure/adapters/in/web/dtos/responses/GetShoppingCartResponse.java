package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.outputs.GetShoppingCartUseCaseOutput;
import com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.dtos.responses.complements.ShoppingCartItemResponse;

import java.math.BigDecimal;
import java.util.List;

public record GetShoppingCartResponse(
        String id,
        BigDecimal totalAmount,
        List<ShoppingCartItemResponse> shoppingCartItems
) {
    public static GetShoppingCartResponse from(GetShoppingCartUseCaseOutput getShoppingCartUseCaseOutput) {
        return new GetShoppingCartResponse(
                getShoppingCartUseCaseOutput.shoppingCart().getId(),
                getShoppingCartUseCaseOutput.shoppingCart().getTotalAmount(),
                ShoppingCartItemResponse.from(getShoppingCartUseCaseOutput.shoppingCartItems())
        );
    }
}
