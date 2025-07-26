package com.miguelsperle.nexbuy.core.infrastructure.adapters.out.providers;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.IDomainEventPublisherProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DomainEventPublisherProvider implements IDomainEventPublisherProvider {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishEvent(Object event) {
        this.applicationEventPublisher.publishEvent(event);
    }
}
