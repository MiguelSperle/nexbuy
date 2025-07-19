package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public record CreateVerificationCodeUseCaseInput(
        User user
) {
    public static CreateVerificationCodeUseCaseInput with(User user) {
        return new CreateVerificationCodeUseCaseInput(user);
    }
}
