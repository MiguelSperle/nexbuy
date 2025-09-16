package com.miguelsperle.nexbuy.module.freight.application.usecases;

import com.miguelsperle.nexbuy.module.freight.application.ports.in.usecases.GetFreightUseCase;
import com.miguelsperle.nexbuy.module.freight.application.ports.out.persistence.FreightRepository;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.GetFreightUseCaseInput;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.outputs.GetFreightUseCaseOutput;
import com.miguelsperle.nexbuy.module.freight.domain.entities.Freight;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;

public class GetFreightUseCaseImpl implements GetFreightUseCase {
    private final FreightRepository freightRepository;

    public GetFreightUseCaseImpl(FreightRepository freightRepository) {
        this.freightRepository = freightRepository;
    }

    @Override
    public GetFreightUseCaseOutput execute(GetFreightUseCaseInput getFreightUseCaseInput) {
        final Freight freight = this.getFreightByOrderId(getFreightUseCaseInput.orderId());

        return GetFreightUseCaseOutput.from(freight);
    }

    private Freight getFreightByOrderId(String orderId) {
        return this.freightRepository.findByOrderId(orderId)
                .orElseThrow(() -> DomainException.with("Freight not found", 404));
    }
}
