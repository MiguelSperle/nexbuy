package com.miguelsperle.nexbuy.core.application.ports.out.providers;

public interface DomainEventPublisherProvider {
    void publishEvent(Object event);
}
