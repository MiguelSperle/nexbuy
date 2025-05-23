package com.miguelsperle.nexbuy.core.domain.abstractions.providers;

public interface IDomainEventPublisherProvider {
    void publishEvent(Object event);
}
