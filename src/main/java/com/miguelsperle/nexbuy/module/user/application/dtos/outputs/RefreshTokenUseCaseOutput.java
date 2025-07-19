package com.miguelsperle.nexbuy.module.user.application.dtos.outputs;

public record RefreshTokenUseCaseOutput(
        String accessToken
) {
    public static RefreshTokenUseCaseOutput from(String accessToken) {
        return new RefreshTokenUseCaseOutput(accessToken);
    }
}

