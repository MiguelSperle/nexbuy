package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.ValidatePasswordResetCodeUseCaseOutput;

public record ValidatePasswordResetCodeResponse(
        Boolean codeIsValid
) {
    public static ValidatePasswordResetCodeResponse from(ValidatePasswordResetCodeUseCaseOutput validatePasswordResetCodeUseCaseOutput) {
        return new ValidatePasswordResetCodeResponse(validatePasswordResetCodeUseCaseOutput.codeIsValid());
    }
}

