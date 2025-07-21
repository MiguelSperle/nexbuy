package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record ResetUserPasswordUseCaseInput(
        String code,
        String password
) {
    public static ResetUserPasswordUseCaseInput with(String code, String password) {
        return new ResetUserPasswordUseCaseInput(code, password);
    }
}

