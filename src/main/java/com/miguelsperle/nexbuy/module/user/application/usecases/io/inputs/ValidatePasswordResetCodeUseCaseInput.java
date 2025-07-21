package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record ValidatePasswordResetCodeUseCaseInput(
        String code
) {
    public static  ValidatePasswordResetCodeUseCaseInput with(String code) {
        return new ValidatePasswordResetCodeUseCaseInput(code);
    }
}
