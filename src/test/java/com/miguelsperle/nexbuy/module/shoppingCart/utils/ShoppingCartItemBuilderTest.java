package com.miguelsperle.nexbuy.module.shoppingCart.utils;

import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.math.BigDecimal;

public class ShoppingCartItemBuilderTest {
    public static ShoppingCartItem create(String shoppingCartId, int quantity, BigDecimal unitPrice) {
        return ShoppingCartItem.with(
                IdentifierUtils.generateUUID(),
                shoppingCartId,
                IdentifierUtils.generateUUID(),
                quantity,
                unitPrice,
                TimeUtils.now()
        );
    }
}
