package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record ValidatePasswordResetCodeUseCaseInput(
        String code
) {
    public static  ValidatePasswordResetCodeUseCaseInput with(String code) {
        return new ValidatePasswordResetCodeUseCaseInput(code);
    }
}
