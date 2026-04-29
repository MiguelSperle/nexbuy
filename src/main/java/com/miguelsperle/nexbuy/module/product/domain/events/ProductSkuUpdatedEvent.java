package com.miguelsperle.nexbuy.module.product.domain.events;

public record ProductSkuUpdatedEvent(
        String id,
        String sku
) {
    public static ProductSkuUpdatedEvent from(String id, String sku) {
        return new ProductSkuUpdatedEvent(id, sku);
    }
}
