package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record RefreshTokenUseCaseInput(
        String refreshToken
) {
    public static RefreshTokenUseCaseInput with(String refreshToken) {
        return new RefreshTokenUseCaseInput(refreshToken);
    }
}
