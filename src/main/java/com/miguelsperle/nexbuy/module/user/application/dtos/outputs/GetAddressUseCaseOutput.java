package com.miguelsperle.nexbuy.module.user.application.dtos.outputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public record GetAddressUseCaseOutput(
        Address address
) {
    public static GetAddressUseCaseOutput from(Address address) {
        return new GetAddressUseCaseOutput(address);
    }
}
