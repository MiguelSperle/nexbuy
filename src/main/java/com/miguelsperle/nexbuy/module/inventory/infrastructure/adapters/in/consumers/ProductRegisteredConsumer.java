package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.consumers;

import com.miguelsperle.nexbuy.module.inventory.application.ports.in.usecases.CreateInventoryUseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CreateInventoryUseCaseInput;
import com.miguelsperle.nexbuy.shared.domain.events.ProductRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRegisteredConsumer {
    private final CreateInventoryUseCase createInventoryUseCase;

    private static final String PRODUCT_REGISTERED_QUEUE = "product.registered.queue";

    @RabbitListener(queues = PRODUCT_REGISTERED_QUEUE)
    public void onMessage(ProductRegisteredEvent productRegisteredEvent) {
        this.createInventoryUseCase.execute(CreateInventoryUseCaseInput.with(
                productRegisteredEvent.id(),
                productRegisteredEvent.sku()
        ));
    }
}
