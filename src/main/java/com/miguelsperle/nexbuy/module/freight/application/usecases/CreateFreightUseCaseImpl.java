package com.miguelsperle.nexbuy.module.freight.application.usecases;

import com.miguelsperle.nexbuy.module.freight.application.abstractions.repositories.FreightRepository;
import com.miguelsperle.nexbuy.module.freight.application.abstractions.usecases.CreateFreightUseCase;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.CreateFreightUseCaseInput;
import com.miguelsperle.nexbuy.module.freight.domain.entities.Freight;

public class CreateFreightUseCaseImpl implements CreateFreightUseCase {
    private final FreightRepository freightRepository;

    public CreateFreightUseCaseImpl(FreightRepository freightRepository) {
        this.freightRepository = freightRepository;
    }
    @Override
    public void execute(CreateFreightUseCaseInput input) {
        final Freight freight = Freight.newFreight(
                input.orderId(),
                input.name(),
                input.companyName(),
                input.price(),
                input.estimatedTime(),
                input.minTime(),
                input.maxTime()
        );

        this.freightRepository.save(freight);
    }
}
