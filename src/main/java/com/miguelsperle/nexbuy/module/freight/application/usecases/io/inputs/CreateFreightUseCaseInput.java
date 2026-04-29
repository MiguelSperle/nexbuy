package com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs;

import java.math.BigDecimal;

public record CreateFreightUseCaseInput(
        String orderId,
        String name,
        String companyName,
        BigDecimal price,
        Integer estimatedTime,
        Integer minTime,
        Integer maxTime
) {
    public static CreateFreightUseCaseInput with(
            String orderId,
            String name,
            String companyName,
            BigDecimal price,
            Integer estimatedTime,
            Integer minTime,
            Integer maxTime
    ) {
        return new CreateFreightUseCaseInput(
                orderId,
                name,
                companyName,
                price,
                estimatedTime,
                minTime,
                maxTime
        );
    }
}
