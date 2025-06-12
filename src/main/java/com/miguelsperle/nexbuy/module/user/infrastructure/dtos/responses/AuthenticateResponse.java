package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.AuthenticateUseCaseOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateResponse {
    private String accessToken;
    private String refreshToken;

    public static AuthenticateResponse fromOutput(AuthenticateUseCaseOutput authenticateUseCaseOutput) {
        return new AuthenticateResponse(authenticateUseCaseOutput.getAccessToken(), authenticateUseCaseOutput.getRefreshToken());
    }
}
