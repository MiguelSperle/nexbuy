package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordRequest(
        @NotBlank(message = "Current password should not neither null nor blank")
        String currentPassword,

        @NotNull(message = "Password should not be null")
        @Size(min = 5, max = 100, message = "Password should contain between 5 and 100 characters")
        String password
) {
}

