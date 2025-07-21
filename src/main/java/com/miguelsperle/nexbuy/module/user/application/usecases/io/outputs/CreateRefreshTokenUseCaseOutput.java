package com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;

public record CreateRefreshTokenUseCaseOutput(
        RefreshToken refreshToken
) {
    public static CreateRefreshTokenUseCaseOutput from(RefreshToken refreshToken) {
        return new CreateRefreshTokenUseCaseOutput(refreshToken);
    }
}

