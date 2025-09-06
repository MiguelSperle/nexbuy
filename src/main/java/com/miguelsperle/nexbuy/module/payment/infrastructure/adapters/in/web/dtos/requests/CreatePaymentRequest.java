package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.dtos.requests;

import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentMethod;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.annotations.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreatePaymentRequest(
        @NotBlank(message = "Order id should not be neither null nor blank")
        String orderId,

        @NotNull(message = "Total amount should not be null")
        @Positive(message = "Total amount should be a positive number")
        BigDecimal totalAmount,

        @ValidEnum(enumClass = PaymentMethod.class, message = "Payment should be either BANK_SLIP or CARD")
        String paymentMethod
) {
}
