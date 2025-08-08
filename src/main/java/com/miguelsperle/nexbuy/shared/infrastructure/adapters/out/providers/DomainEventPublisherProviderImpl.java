package com.miguelsperle.nexbuy.shared.infrastructure.adapters.out.providers;

import com.miguelsperle.nexbuy.shared.application.ports.out.providers.DomainEventPublisherProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DomainEventPublisherProviderImpl implements DomainEventPublisherProvider {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishEvent(Object event) {
        this.applicationEventPublisher.publishEvent(event);
    }
}
