package com.miguelsperle.nexbuy.module.user.infrastructure.rest.dtos.res;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.AuthenticateUseCaseOutput;

public record AuthenticateResponse(
        String accessToken,
        String refreshToken
) {
    public static AuthenticateResponse from(AuthenticateUseCaseOutput authenticateUseCaseOutput) {
        return new AuthenticateResponse(
                authenticateUseCaseOutput.accessToken(),
                authenticateUseCaseOutput.refreshToken()
        );
    }
}

