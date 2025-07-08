package com.miguelsperle.nexbuy.module.user.application.dtos.outputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public record GetAddressUseCaseOutput(
        Address address
) {
}
