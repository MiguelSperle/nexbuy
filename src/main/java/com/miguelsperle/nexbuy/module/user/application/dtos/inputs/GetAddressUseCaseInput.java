package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record GetAddressUseCaseInput(
        String addressId
) {
    public static GetAddressUseCaseInput with(String addressId) {
        return new GetAddressUseCaseInput(addressId);
    }
}
