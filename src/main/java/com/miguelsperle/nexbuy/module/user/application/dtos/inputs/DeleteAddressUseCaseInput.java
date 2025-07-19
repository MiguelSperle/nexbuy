package com.miguelsperle.nexbuy.module.user.application.dtos.inputs;

public record DeleteAddressUseCaseInput(
        String addressId
) {
    public static DeleteAddressUseCaseInput with(String addressId) {
        return new DeleteAddressUseCaseInput(addressId);
    }
}
