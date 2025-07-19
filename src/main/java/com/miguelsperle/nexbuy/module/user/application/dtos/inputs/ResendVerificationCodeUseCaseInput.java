package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record ResendVerificationCodeUseCaseInput(
        String email
) {
    public static ResendVerificationCodeUseCaseInput with(String email) {
        return new ResendVerificationCodeUseCaseInput(email);
    }
}
