package com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs;

public record AuthenticateUseCaseOutput(
        String accessToken,
        String refreshToken
) {
    public static AuthenticateUseCaseOutput from(String accessToken, String refreshToken) {
        return new AuthenticateUseCaseOutput(accessToken, refreshToken);
    }
}

