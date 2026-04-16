package com.miguelsperle.nexbuy.module.payment.infrastructure.rest.dtos.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreatePaymentRequest(
        @NotBlank(message = "Order id should not be neither null nor blank")
        String orderId,

        @NotNull(message = "Total amount should not be null")
        @Positive(message = "Total amount should be a positive number")
        BigDecimal totalAmount
) {
}
