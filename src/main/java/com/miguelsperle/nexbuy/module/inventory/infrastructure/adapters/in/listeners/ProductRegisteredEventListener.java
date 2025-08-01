package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.listeners;

import com.miguelsperle.nexbuy.core.domain.events.ProductRegisteredEvent;
import com.miguelsperle.nexbuy.core.infrastructure.adapters.exceptions.EventProcessingFailureException;
import com.miguelsperle.nexbuy.module.inventory.application.ports.in.CreateInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CreateInventoryUseCaseInput;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRegisteredEventListener {
    private final CreateInventoryUseCase createInventoryUseCase;

    private static final Logger log = LoggerFactory.getLogger(ProductRegisteredEventListener.class);

    @Async
    @EventListener
    @Retryable(
            retryFor = {EventProcessingFailureException.class},
            maxAttempts = 4,
            backoff = @Backoff( // Exponential Backoff Configuration
                    delay = 2500,
                    multiplier = 2,
                    maxDelay = 10000
            )
    )
    public void handleProductRegisteredEvent(ProductRegisteredEvent productRegisteredEvent) {
        try {
            this.createInventoryUseCase.execute(CreateInventoryUseCaseInput.with(
                    productRegisteredEvent.sku()
            ));
        } catch (Exception exception) {
            log.error("Failed to process event - Type: [{}] - Details: [{}]",
                    productRegisteredEvent.getClass().getSimpleName(),
                    productRegisteredEvent,
                    exception
            );
            throw EventProcessingFailureException.with("Failed to process event", exception);
        }
    }

    @Recover
    public void recover(EventProcessingFailureException eventProcessingFailureException, ProductRegisteredEvent productRegisteredEvent) {
        log.error("All retry attempts to process the event failed - Type: [{}] - Details: [{}}",
                productRegisteredEvent.getClass().getSimpleName(),
                productRegisteredEvent,
                eventProcessingFailureException
        );
    }
}
