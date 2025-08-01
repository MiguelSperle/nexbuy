package com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;

public record CreateVerificationCodeUseCaseOutput(
        UserCode userCode
) {
    public static CreateVerificationCodeUseCaseOutput from(UserCode userCode) {
        return new CreateVerificationCodeUseCaseOutput(userCode);
    }
}
