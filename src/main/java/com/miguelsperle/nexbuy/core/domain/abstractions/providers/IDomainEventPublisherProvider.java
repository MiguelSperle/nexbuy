package com.miguelsperle.nexbuy.core.domain.abstractions.providers;

public interface IDomainEventPublisherProvider {
    void publish(Object event);
}
