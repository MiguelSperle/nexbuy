package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record AuthenticateUseCaseInput(
        String email,
        String password
) {
}
