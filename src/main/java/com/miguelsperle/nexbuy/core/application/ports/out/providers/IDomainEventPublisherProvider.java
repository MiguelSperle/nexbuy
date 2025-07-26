package com.miguelsperle.nexbuy.core.application.ports.out.providers;

public interface IDomainEventPublisherProvider {
    void publishEvent(Object event);
}
