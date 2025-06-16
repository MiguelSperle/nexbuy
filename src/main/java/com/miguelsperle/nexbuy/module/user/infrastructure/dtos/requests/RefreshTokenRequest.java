package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {
    @NotBlank(message = "Refresh token should not be neither null nor empty")
    private String refreshToken;
}
