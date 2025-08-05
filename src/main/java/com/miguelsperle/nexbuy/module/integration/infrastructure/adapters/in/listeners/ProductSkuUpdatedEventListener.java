package com.miguelsperle.nexbuy.module.integration.infrastructure.adapters.in.listeners;

import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.domain.exceptions.InventoryNotFoundException;
import com.miguelsperle.nexbuy.module.product.domain.events.ProductSkuUpdatedEvent;
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
public class ProductSkuUpdatedEventListener {
    private final InventoryRepository inventoryRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductSkuUpdatedEventListener.class);

    @Async
    @EventListener
    @Retryable(
            retryFor = {EventProcessingFailureException.class},
            maxAttempts = 4,
            backoff = @Backoff( // Exponential Backoff Configuration
                    delay = 3000,
                    multiplier = 2,
                    maxDelay = 24000
            )
    )
    public void handleProductSkuUpdatedEvent(ProductSkuUpdatedEvent productSkuUpdatedEvent) {
        try {
            final Inventory inventory = this.getInventoryByProductId(productSkuUpdatedEvent.id());

            final Inventory updatedInventory = inventory.withSku(productSkuUpdatedEvent.sku());

            this.saveInventory(updatedInventory);
        } catch (Exception exception) {
            log.error("Failed to process event - Type: [{}] - Details: [{}]",
                    productSkuUpdatedEvent.getClass().getSimpleName(),
                    productSkuUpdatedEvent,
                    exception
            );
            throw EventProcessingFailureException.with("Failed to process event", exception);
        }
    }

    private Inventory getInventoryByProductId(String productId) {
        return this.inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> InventoryNotFoundException.with("Inventory not found"));
    }

    private void saveInventory(Inventory inventory) {
        this.inventoryRepository.save(inventory);
    }

    @Recover
    public void recover(EventProcessingFailureException eventProcessingFailureException, ProductSkuUpdatedEvent productSkuUpdatedEvent) {
        log.error("All retry attempts to process the event failed - Type: [{}] - Details: [{}}",
                productSkuUpdatedEvent.getClass().getSimpleName(),
                productSkuUpdatedEvent,
                eventProcessingFailureException
        );
    }
}
