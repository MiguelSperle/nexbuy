package com.miguelsperle.nexbuy.module.user.application.dtos.outputs;

public record ValidatePasswordResetCodeUseCaseOutput(
        Boolean codeIsValid
) {
    public static ValidatePasswordResetCodeUseCaseOutput from(Boolean codeIsValid) {
        return new ValidatePasswordResetCodeUseCaseOutput(codeIsValid);
    }
}

