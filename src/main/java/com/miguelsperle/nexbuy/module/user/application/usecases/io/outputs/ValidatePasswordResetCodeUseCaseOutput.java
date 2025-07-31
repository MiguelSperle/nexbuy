package com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs;

public record ValidatePasswordResetCodeUseCaseOutput(
        Boolean isValid
) {
    public static ValidatePasswordResetCodeUseCaseOutput from(Boolean isValid) {
        return new ValidatePasswordResetCodeUseCaseOutput(isValid);
    }
}

