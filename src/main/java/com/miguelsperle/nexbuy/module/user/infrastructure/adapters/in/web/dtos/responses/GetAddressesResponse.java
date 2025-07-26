package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressesUseCaseOutput;

import java.util.List;

public record GetAddressesResponse(
        String id,
        String addressLine,
        String addressNumber,
        String zipCode,
        String neighborhood,
        String city,
        String uf,
        String complement
) {
    public static List<GetAddressesResponse> from(GetAddressesUseCaseOutput getAddressesUseCaseOutput) {
        return getAddressesUseCaseOutput.addresses().stream().map(address -> new GetAddressesResponse(
                address.getId(),
                address.getAddressLine(),
                address.getAddressNumber(),
                address.getZipCode(),
                address.getNeighborhood(),
                address.getCity(),
                address.getUf(),
                address.getComplement()
        )).toList();
    }
}
