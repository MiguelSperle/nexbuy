package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreatePasswordResetCodeRequest(
        @NotBlank(message = "Email should not be neither null nor blank")
        @Email(message = "Email should be valid")
        String email
) {
}

