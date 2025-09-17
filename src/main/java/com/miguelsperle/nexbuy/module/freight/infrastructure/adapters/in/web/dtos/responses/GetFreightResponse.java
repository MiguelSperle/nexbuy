package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.freight.application.usecases.io.outputs.GetFreightUseCaseOutput;

import java.math.BigDecimal;

public record GetFreightResponse(
        String name,
        String companyName,
        BigDecimal price,
        Integer estimatedTime,
        Integer minTime,
        Integer maxTime
) {
    public static GetFreightResponse from(GetFreightUseCaseOutput getFreightUseCaseOutput) {
        return new GetFreightResponse(
                getFreightUseCaseOutput.freight().getName(),
                getFreightUseCaseOutput.freight().getCompanyName(),
                getFreightUseCaseOutput.freight().getPrice(),
                getFreightUseCaseOutput.freight().getEstimatedTime(),
                getFreightUseCaseOutput.freight().getMinTime(),
                getFreightUseCaseOutput.freight().getMaxTime()
        );
    }
}
