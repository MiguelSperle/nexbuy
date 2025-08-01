package com.miguelsperle.nexbuy.core.domain.events;

public record ProductRegisteredEvent(
        String id,
        String sku
) {
    public static ProductRegisteredEvent from(String id, String sku) {
        return new ProductRegisteredEvent(id, sku);
    }
}
