package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.RefreshTokenUseCaseOutput;

public record RefreshTokenResponse(
        String accessToken
) {
    public static RefreshTokenResponse from(RefreshTokenUseCaseOutput refreshTokenUseCaseOutput) {
        return new RefreshTokenResponse(refreshTokenUseCaseOutput.accessToken());
    }
}