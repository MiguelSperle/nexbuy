package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record CreatePasswordResetCodeUseCaseInput(
        String email
) {
    public static CreatePasswordResetCodeUseCaseInput with(String email) {
        return new CreatePasswordResetCodeUseCaseInput(email);
    }
}
