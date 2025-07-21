package com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs;

public record ValidatePasswordResetCodeUseCaseOutput(
        Boolean codeIsValid
) {
    public static ValidatePasswordResetCodeUseCaseOutput from(Boolean codeIsValid) {
        return new ValidatePasswordResetCodeUseCaseOutput(codeIsValid);
    }
}

