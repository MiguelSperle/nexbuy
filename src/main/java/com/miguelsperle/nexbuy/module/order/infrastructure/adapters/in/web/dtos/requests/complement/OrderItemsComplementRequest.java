package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.dtos.requests.complement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemsComplementRequest(
        @NotBlank(message = "Product id should not neither be null nor blank")
        String productId,

        @NotNull(message = "Quantity should not be null")
        @Positive(message = "Quantity should be a positive number")
        Integer quantity,

        @NotNull(message = "Unit price should not be null")
        @Positive(message = "Unit price should be a positive number")
        BigDecimal unitPrice
) {
}
