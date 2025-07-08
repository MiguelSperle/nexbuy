package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.ValidatePasswordResetCodeUseCaseOutput;

public record ValidatePasswordResetCodeResponse(
        Boolean codeIsValid
) {
    public static ValidatePasswordResetCodeResponse fromOutput(ValidatePasswordResetCodeUseCaseOutput validatePasswordResetCodeUseCaseOutput) {
        return new ValidatePasswordResetCodeResponse(validatePasswordResetCodeUseCaseOutput.codeIsValid());
    }
}

