package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserToVerifiedRequest(
        @NotBlank(message = "Code should not be neither null nor blank")
        String code
) {
}
