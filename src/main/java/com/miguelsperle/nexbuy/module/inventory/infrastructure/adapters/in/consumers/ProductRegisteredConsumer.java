package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.consumers;

import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.shared.domain.events.ProductRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRegisteredConsumer {
    private final InventoryRepository inventoryRepository;

    private static final String PRODUCT_REGISTERED_QUEUE = "product.registered.queue";

    @RabbitListener(queues = PRODUCT_REGISTERED_QUEUE)
    public void onMessage(ProductRegisteredEvent productRegisteredEvent) {
        final Inventory newInventory = Inventory.newInventory(productRegisteredEvent.id(), productRegisteredEvent.sku());

        this.saveInventory(newInventory);
    }

    private void saveInventory(Inventory inventory) {
        this.inventoryRepository.save(inventory);
    }
}
