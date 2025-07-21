package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record CreateVerificationCodeUseCaseInput(
        String userId
) {
    public static CreateVerificationCodeUseCaseInput with(String userId) {
        return new CreateVerificationCodeUseCaseInput(userId);
    }
}
