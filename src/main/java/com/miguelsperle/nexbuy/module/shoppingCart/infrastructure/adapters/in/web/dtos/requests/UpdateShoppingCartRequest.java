package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateShoppingCartRequest(
        @NotNull(message = "Quantity should not be null")
        @Positive(message = "Quantity should be a positive number")
        Integer quantity
) {
}
