package com.miguelsperle.nexbuy.module.freight.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.freight.domain.entities.Freight;

public record GetFreightUseCaseOutput(
        Freight freight
) {
    public static GetFreightUseCaseOutput from(Freight freight) {
        return new GetFreightUseCaseOutput(freight);
    }
}
