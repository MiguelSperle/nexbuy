package com.miguelsperle.nexbuy.shared.domain.events;

import java.math.BigDecimal;

public record ProductPriceUpdatedEvent(
        String id,
        BigDecimal price
) {
    public static ProductPriceUpdatedEvent from(String id, BigDecimal price) {
        return new ProductPriceUpdatedEvent(id, price);
    }
}
