package com.miguelsperle.nexbuy.core.domain.events;

public record ProductDeletedEvent(
        String id
) {
    public static ProductDeletedEvent from(String id) {
        return new ProductDeletedEvent(id);
    }
}
