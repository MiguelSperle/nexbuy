package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record UpdateUserPasswordUseCaseInput(
        String currentPassword,
        String password
) {
    public static UpdateUserPasswordUseCaseInput with(String currentPassword, String password) {
        return new UpdateUserPasswordUseCaseInput(currentPassword, password);
    }
}

