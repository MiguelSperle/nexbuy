package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record AuthenticateUseCaseInput(
        String email,
        String password
) {
    public static AuthenticateUseCaseInput with(String email, String password) {
        return new AuthenticateUseCaseInput(email, password);
    }
}
