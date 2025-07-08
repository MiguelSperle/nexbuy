package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record ValidatePasswordResetCodeRequest(
        @NotBlank(message = "Code should not be neither null nor blank")
        String code
) {
}

