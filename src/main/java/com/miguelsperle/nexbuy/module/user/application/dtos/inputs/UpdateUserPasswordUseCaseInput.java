package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record UpdateUserPasswordUseCaseInput(
        String currentPassword,
        String password
) {
}

