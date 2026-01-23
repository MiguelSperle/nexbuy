package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.consumers;

import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.shared.domain.events.ProductSkuUpdatedEvent;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductSkuUpdatedConsumer {
    private final InventoryRepository inventoryRepository;

    private static final String PRODUCT_SKU_UPDATED_QUEUE = "product.sku.updated.queue";

    @RabbitListener(queues = PRODUCT_SKU_UPDATED_QUEUE)
    public void onMessage(ProductSkuUpdatedEvent productSkuUpdatedEvent) {
        final Inventory inventory = this.getInventoryByProductId(productSkuUpdatedEvent.id());

        final Inventory updatedInventory = inventory.withSku(productSkuUpdatedEvent.sku());

        this.saveInventory(updatedInventory);
    }

    private Inventory getInventoryByProductId(String productId) {
        return this.inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> NotFoundException.with("Inventory not found"));
    }

    private void saveInventory(Inventory inventory) {
        this.inventoryRepository.save(inventory);
    }
}
