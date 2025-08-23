package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.dtos.requests;

import com.miguelsperle.nexbuy.module.shoppingCart.domain.enums.ShoppingCartItemAction;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.annotations.ValidEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateShoppingCartRequest(
        @NotNull(message = "Quantity should not be null")
        @Positive(message = "Quantity should be a positive number")
        Integer quantity,

        @ValidEnum(enumClass = ShoppingCartItemAction.class, message = "Action should be either ADD or REMOVE")
        String action
) {
}
