package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.rest.dtos.res.complements;

import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.List;

public record ShoppingCartItemComplementResponse(
        String id,
        String productId,
        Integer quantity,
        BigDecimal unitPrice
) {
    public static List<ShoppingCartItemComplementResponse> from(List<ShoppingCartItem> shoppingCartItems) {
        return shoppingCartItems.stream().map(shoppingCartItem -> new ShoppingCartItemComplementResponse(
                shoppingCartItem.getId(),
                shoppingCartItem.getProductId(),
                shoppingCartItem.getQuantity(),
                shoppingCartItem.getUnitPrice()
        )).toList();
    }
}
