package com.miguelsperle.nexbuy.module.payments.infrastructure.adapters.in.web.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePaymentMethodRequest(
        @NotBlank(message = "Name should not be neither null nor blank")
        @Size(max = 50, message = "Name should not exceed 50 characters")
        String name
) {
}
