package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public record CreateVerificationCodeUseCaseInput(
        String userId
) {
    public static CreateVerificationCodeUseCaseInput with(String userId) {
        return new CreateVerificationCodeUseCaseInput(userId);
    }
}
