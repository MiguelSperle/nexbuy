package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record UpdateUserUseCaseInput(
        String firstName,
        String lastName,
        String phoneNumber
) {
}

