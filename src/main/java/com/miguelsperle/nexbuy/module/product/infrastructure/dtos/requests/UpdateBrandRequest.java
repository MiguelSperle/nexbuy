package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateBrandRequest(
        @NotBlank(message = "Name should not be neither null nor blank")
        @Size(max = 50, message = "Name should not exceed 50 characters")
        String name,

        @NotBlank(message = "Description should not be neither null nor blank")
        @Size(max = 500, message = "Description should not exceed 500 characters")
        String description
) {
}

