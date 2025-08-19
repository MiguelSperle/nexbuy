package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.in.web.dtos.requests;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateCouponRequest(
        @NotBlank(message = "Code should not be neither null nor blank")
        @Size(max = 50, message = "Code should not exceed 50 characters")
        String code,

        @NotNull(message = "Percentage should not be null")
        @Positive(message = "Percentage should be a positive number")
        Integer percentage,

        @NotNull(message = "Minimum purchase amount should not be null")
        @PositiveOrZero(message = "Minimum purchase amount should be zero or a positive number")
        @Digits(integer = 17, fraction = 2, message = "Minimum purchase amount should have up to 17 digits before the decimal point and 2 after")
        BigDecimal minimumPurchaseAmount,

        @NotNull(message = "Is active should not be null")
        Boolean isActive,

        @NotNull(message = "Expires in should not be null")
        @Future(message = "Expires in should be in the future")
        LocalDateTime expiresIn
) {
}
