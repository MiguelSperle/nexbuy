package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record AuthenticateUseCaseInput(
        String email,
        String password
) {
    public static AuthenticateUseCaseInput with(String email, String password) {
        return new AuthenticateUseCaseInput(email, password);
    }
}
