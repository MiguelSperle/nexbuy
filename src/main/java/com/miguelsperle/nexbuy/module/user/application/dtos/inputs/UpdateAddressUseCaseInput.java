package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record UpdateAddressUseCaseInput(
        String addressId,
        String addressLine,
        String addressNumber,
        String zipCode,
        String neighborhood,
        String city,
        String uf,
        String complement
) {
    public static UpdateAddressUseCaseInput with(
            String addressId,
            String addressLine,
            String addressNumber,
            String zipCode,
            String neighborhood,
            String city,
            String uf,
            String complement
    ) {
        return new UpdateAddressUseCaseInput(
                addressId,
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

