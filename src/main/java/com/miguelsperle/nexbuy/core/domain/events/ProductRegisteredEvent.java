package com.miguelsperle.nexbuy.core.domain.events;

public record ProductRegisteredEvent(
        String sku
) {
    public static ProductRegisteredEvent from(String sku) {
        return new ProductRegisteredEvent(sku);
    }
}
