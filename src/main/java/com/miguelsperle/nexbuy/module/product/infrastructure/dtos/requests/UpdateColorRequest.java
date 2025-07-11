package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateColorRequest(
        @NotBlank(message = "Name should not be neither null nor blank")
        @Size(max = 25, message = "Name should not exceed 25 characters")
        String name
) {
}
