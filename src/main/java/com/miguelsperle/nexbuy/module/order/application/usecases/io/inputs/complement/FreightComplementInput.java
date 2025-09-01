package com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.complement;

import java.math.BigDecimal;

public record FreightComplementInput(
        String name,
        String companyName,
        BigDecimal price,
        Integer estimatedTime,
        Integer minTime,
        Integer maxTime
) {
    public static FreightComplementInput with(
            String name,
            String companyName,
            BigDecimal price,
            Integer estimatedTime,
            Integer minTime,
            Integer maxTime
    ) {
        return new FreightComplementInput(
                name,
                companyName,
                price,
                estimatedTime,
                minTime,
                maxTime
        );
    }
}
