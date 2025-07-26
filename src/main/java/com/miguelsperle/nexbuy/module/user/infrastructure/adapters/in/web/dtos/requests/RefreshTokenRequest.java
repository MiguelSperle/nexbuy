package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token should not be neither null nor blank")
        String refreshToken
) {
}
