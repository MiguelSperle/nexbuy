package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.consumers.reprocessing;

import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.domain.events.ProductSkuUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductSkuUpdatedDlqConsumer {
    private final MessageProducer messageProducer;

    private static final String PRODUCT_SKU_UPDATED_DLQ_QUEUE = "product.sku.updated.dlq.queue";
    private static final String PRODUCT_SKU_UPDATED_EXCHANGE = "product.sku.updated.exchange";
    private static final String PRODUCT_SKU_UPDATED_ROUTING_KEY = "product.sku.updated.routing.key";

    @RabbitListener(queues = PRODUCT_SKU_UPDATED_DLQ_QUEUE)
    public void onMessage(ProductSkuUpdatedEvent productSkuUpdatedEvent) {
        this.messageProducer.publish(PRODUCT_SKU_UPDATED_EXCHANGE, PRODUCT_SKU_UPDATED_ROUTING_KEY, productSkuUpdatedEvent);
    }
}
