package com.miguelsperle.nexbuy.shared.application.ports.out.providers;

public interface DomainEventPublisherProvider {
    void publishEvent(Object event);
}
