package com.miguelsperle.nexbuy.core.infrastructure.providers;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DomainEventPublisherProvider implements IDomainEventPublisherProvider {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(Object event) {
        this.applicationEventPublisher.publishEvent(event);
    }
}
