package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record CreateAddressUseCaseInput(
        String addressLine,
        String addressNumber,
        String zipCode,
        String neighborhood,
        String city,
        String uf,
        String complement
) {
    public static CreateAddressUseCaseInput with(
            String addressLine,
            String addressNumber,
            String zipCode,
            String neighborhood,
            String city,
            String uf,
            String complement
    ) {
        return new CreateAddressUseCaseInput(
                addressLine,
                addressNumber,
                zipCode,
                neighborhood,
                city,
                uf,
                complement
        );
    }
}

