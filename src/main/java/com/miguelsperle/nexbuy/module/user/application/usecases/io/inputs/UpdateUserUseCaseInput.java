package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record UpdateUserUseCaseInput(
        String firstName,
        String lastName,
        String phoneNumber
) {
    public static UpdateUserUseCaseInput with(
            String firstName,
            String lastName,
            String phoneNumber
    ) {
        return new UpdateUserUseCaseInput(
                firstName,
                lastName,
                phoneNumber
        );
    }
}

