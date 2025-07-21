package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record ResendVerificationCodeUseCaseInput(
        String email
) {
    public static ResendVerificationCodeUseCaseInput with(String email) {
        return new ResendVerificationCodeUseCaseInput(email);
    }
}
