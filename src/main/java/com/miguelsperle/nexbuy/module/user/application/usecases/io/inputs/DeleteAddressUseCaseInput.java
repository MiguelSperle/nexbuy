package com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs;

public record DeleteAddressUseCaseInput(
        String addressId
) {
    public static DeleteAddressUseCaseInput with(String addressId) {
        return new DeleteAddressUseCaseInput(addressId);
    }
}
