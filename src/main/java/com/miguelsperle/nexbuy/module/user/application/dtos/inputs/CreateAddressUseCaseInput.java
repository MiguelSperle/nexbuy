package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record CreateAddressUseCaseInput(
        String addressLine,
        String addressNumber,
        String zipCode,
        String neighborhood,
        String city,
        String uf,
        String complement
) {
}

