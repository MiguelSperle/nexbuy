package com.miguelsperle.nexbuy.module.integration.infrastructure.adapters.in.listeners;

import com.miguelsperle.nexbuy.module.inventory.application.ports.in.usecases.CreateInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CreateInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.events.ProductRegisteredEvent;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.exceptions.EventProcessingFailureException;
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
            maxAttempts = 5,
            backoff = @Backoff( // Exponential Backoff Configuration
                    delay = 3000,
                    multiplier = 2,
                    maxDelay = 24000
            )
    )
    public void handleProductRegisteredEvent(ProductRegisteredEvent productRegisteredEvent) {
        try {
            this.createInventoryUseCase.execute(CreateInventoryUseCaseInput.with(
                    productRegisteredEvent.id(),
                    productRegisteredEvent.sku()
            ));
        } catch (Exception ex) {
            log.error("Failed to process event - Type: [{}] - Details: [{}]",
                    productRegisteredEvent.getClass().getSimpleName(),
                    productRegisteredEvent,
                    ex
            );
            throw EventProcessingFailureException.with("Failed to process event", ex);
        }
    }

    @Recover
    public void recover(EventProcessingFailureException ex, ProductRegisteredEvent productRegisteredEvent) {
        log.error("All retry attempts to process the event failed - Type: [{}] - Details: [{}}",
                productRegisteredEvent.getClass().getSimpleName(),
                productRegisteredEvent,
                ex
        );
    }
}
