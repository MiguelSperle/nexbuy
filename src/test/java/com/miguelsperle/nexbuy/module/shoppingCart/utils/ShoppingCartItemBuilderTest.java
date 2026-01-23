package com.miguelsperle.nexbuy.module.shoppingCart.utils;

import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class ShoppingCartItemBuilderTest {
    public static ShoppingCartItem create(String shoppingCartId) {
        return ShoppingCartItem.with(
                IdentifierUtils.generateUUID(),
                shoppingCartId,
                IdentifierUtils.generateUUID(),
                1,
                DecimalUtils.valueOf(100),
                TimeUtils.now()
        );
    }
}
