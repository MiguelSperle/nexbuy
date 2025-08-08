package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.in.web.dtos.requests;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ApplyCouponRequest(
        @NotNull(message = "Total amount should not be null")
        @Positive(message = "Total amount should be a positive number")
        @Digits(integer = 17, fraction = 2, message = "Total amount should have up to 17 digits before the decimal point and 2 after")
        BigDecimal totalAmount
) {
}
