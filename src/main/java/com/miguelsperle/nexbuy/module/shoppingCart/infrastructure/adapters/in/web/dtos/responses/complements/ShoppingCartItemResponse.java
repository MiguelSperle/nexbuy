package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.dtos.responses.complements;

import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.List;

public record ShoppingCartItemResponse(
        String id,
        String productId,
        Integer quantity,
        BigDecimal unitPrice
) {
    public static List<ShoppingCartItemResponse> from(List<ShoppingCartItem> shoppingCartItems) {
        return shoppingCartItems.stream().map(shoppingCartItem -> new ShoppingCartItemResponse(
                shoppingCartItem.getId(),
                shoppingCartItem.getProductId(),
                shoppingCartItem.getQuantity(),
                shoppingCartItem.getUnitPrice()
        )).toList();
    }
}
