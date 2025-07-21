package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record GetAddressUseCaseInput(
        String addressId
) {
    public static GetAddressUseCaseInput with(String addressId) {
        return new GetAddressUseCaseInput(addressId);
    }
}
