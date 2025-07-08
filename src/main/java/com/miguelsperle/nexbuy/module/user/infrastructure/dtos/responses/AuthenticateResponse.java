package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.AuthenticateUseCaseOutput;

public record AuthenticateResponse(
        String accessToken,
        String refreshToken
) {
    public static AuthenticateResponse fromOutput(AuthenticateUseCaseOutput authenticateUseCaseOutput) {
        return new AuthenticateResponse(
                authenticateUseCaseOutput.accessToken(),
                authenticateUseCaseOutput.refreshToken()
        );
    }
}

