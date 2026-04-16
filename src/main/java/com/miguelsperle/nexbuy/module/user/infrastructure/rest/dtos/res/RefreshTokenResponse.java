package com.miguelsperle.nexbuy.module.user.infrastructure.rest.dtos.res;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.RefreshTokenUseCaseOutput;

public record RefreshTokenResponse(
        String accessToken
) {
    public static RefreshTokenResponse from(RefreshTokenUseCaseOutput refreshTokenUseCaseOutput) {
        return new RefreshTokenResponse(refreshTokenUseCaseOutput.accessToken());
    }
}