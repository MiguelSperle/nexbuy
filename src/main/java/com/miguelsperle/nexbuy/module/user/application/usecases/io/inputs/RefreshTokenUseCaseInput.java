package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record RefreshTokenUseCaseInput(
        String refreshToken
) {
    public static RefreshTokenUseCaseInput with(String refreshToken) {
        return new RefreshTokenUseCaseInput(refreshToken);
    }
}
