package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record UpdateUserToVerifiedUseCaseInput(
        String code
) {
    public static UpdateUserToVerifiedUseCaseInput with(String code) {
        return new UpdateUserToVerifiedUseCaseInput(code);
    }
}
