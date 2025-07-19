package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record CreatePasswordResetCodeUseCaseInput(
        String email
) {
    public static CreatePasswordResetCodeUseCaseInput with(String email) {
        return new CreatePasswordResetCodeUseCaseInput(email);
    }
}
