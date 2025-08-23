package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.web.dtos.requests;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record AddToShoppingCartRequest(
        @NotBlank(message = "Product id should not be neither null nor blank")
        String productId,

        @NotNull(message = "Unit price should not be null")
        @PositiveOrZero(message = "Unit price should be zero or a positive number")
        @Digits(integer = 17, fraction = 2, message = "Unit price should have up to 17 digits before the decimal point and 2 after")
        BigDecimal unitPrice
) {
}
