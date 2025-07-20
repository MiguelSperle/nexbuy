package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record CreateRefreshTokenUseCaseInput(
        String userId
) {
    public static CreateRefreshTokenUseCaseInput with(String userId) {
        return new CreateRefreshTokenUseCaseInput(userId);
    }
}
