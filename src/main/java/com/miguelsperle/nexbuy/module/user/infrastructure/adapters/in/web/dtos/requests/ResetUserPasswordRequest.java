package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ResetUserPasswordRequest(
        @NotBlank(message = "Code should not be neither null nor blank")
        String code,

        @NotNull(message = "Password should not be null")
        @Size(min = 5, max = 100, message = "Password should contain between 5 and 100 characters")
        String password
) {
}

