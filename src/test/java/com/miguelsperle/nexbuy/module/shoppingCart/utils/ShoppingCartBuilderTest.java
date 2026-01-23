package com.miguelsperle.nexbuy.module.shoppingCart.utils;

import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class ShoppingCartBuilderTest {
    public static ShoppingCart create() {
        return ShoppingCart.with(
                IdentifierUtils.generateUUID(),
                IdentifierUtils.generateUUID(),
                DecimalUtils.valueOf(100),
                TimeUtils.now()
        );
    }
}
