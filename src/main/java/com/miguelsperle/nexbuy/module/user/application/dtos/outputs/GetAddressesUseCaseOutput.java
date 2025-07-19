package com.miguelsperle.nexbuy.module.user.application.dtos.outputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

import java.util.List;

public record GetAddressesUseCaseOutput(
        List<Address> addresses
) {
    public static GetAddressesUseCaseOutput from(List<Address> addresses) {
        return new GetAddressesUseCaseOutput(addresses);
    }
}

