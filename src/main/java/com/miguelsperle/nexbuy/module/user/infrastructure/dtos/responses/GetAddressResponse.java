package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAddressUseCaseOutput;

public record GetAddressResponse(
        String id,
        String addressLine,
        String addressNumber,
        String zipCode,
        String neighborhood,
        String city,
        String uf,
        String complement
) {
    public static GetAddressResponse fromOutput(GetAddressUseCaseOutput getAddressUseCaseOutput) {
        return new GetAddressResponse(
                getAddressUseCaseOutput.address().getId(),
                getAddressUseCaseOutput.address().getAddressLine(),
                getAddressUseCaseOutput.address().getAddressNumber(),
                getAddressUseCaseOutput.address().getZipCode(),
                getAddressUseCaseOutput.address().getNeighborhood(),
                getAddressUseCaseOutput.address().getCity(),
                getAddressUseCaseOutput.address().getUf(),
                getAddressUseCaseOutput.address().getComplement()
        );
    }
}
