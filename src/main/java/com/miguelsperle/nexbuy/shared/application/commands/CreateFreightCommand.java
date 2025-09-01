package com.miguelsperle.nexbuy.shared.application.commands;

import java.math.BigDecimal;

public record CreateFreightCommand(
        String orderId,
        String name,
        String companyName,
        BigDecimal price,
        Integer estimatedTime,
        Integer minTime,
        Integer maxTime
) {
    public static CreateFreightCommand with(
            String orderId,
            String name,
            String companyName,
            BigDecimal price,
            Integer estimatedTime,
            Integer minTime,
            Integer maxTime
    ) {
        return new CreateFreightCommand(
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
