package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.RefreshTokenUseCaseOutput;

public record RefreshTokenResponse(
        String accessToken
) {
    public static RefreshTokenResponse from(RefreshTokenUseCaseOutput refreshTokenUseCaseOutput) {
        return new RefreshTokenResponse(refreshTokenUseCaseOutput.accessToken());
    }
}