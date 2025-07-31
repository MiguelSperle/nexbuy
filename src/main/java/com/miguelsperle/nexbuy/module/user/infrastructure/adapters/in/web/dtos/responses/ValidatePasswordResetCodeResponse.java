package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.ValidatePasswordResetCodeUseCaseOutput;

public record ValidatePasswordResetCodeResponse(
        Boolean isValid
) {
    public static ValidatePasswordResetCodeResponse from(ValidatePasswordResetCodeUseCaseOutput validatePasswordResetCodeUseCaseOutput) {
        return new ValidatePasswordResetCodeResponse(validatePasswordResetCodeUseCaseOutput.isValid());
    }
}

