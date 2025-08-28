package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.web.dtos.responses.complement;

public record DeliveryComplementResponse(
        Integer estimatedTime,
        Integer minTime,
        Integer maxTime
) {
    public static DeliveryComplementResponse from(
            Integer estimatedTime,
            Integer minTime,
            Integer maxTime
    ) {
        return new DeliveryComplementResponse(
                estimatedTime,
                minTime,
                maxTime
        );
    }
}
