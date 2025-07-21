package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record UpdateUserPasswordUseCaseInput(
        String currentPassword,
        String password
) {
    public static UpdateUserPasswordUseCaseInput with(String currentPassword, String password) {
        return new UpdateUserPasswordUseCaseInput(currentPassword, password);
    }
}

