package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.consumers.reprocessing;

import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.domain.events.ProductPriceUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPriceUpdatedDlqConsumer {
    private final MessageProducer messageProducer;

    private static final String PRODUCT_PRICE_UPDATED_DLQ_QUEUE = "product.price.updated.dlq.queue";
    private static final String PRODUCT_UPDATED_EXCHANGE = "product.updated.exchange";
    private static final String PRODUCT_PRICE_UPDATED_ROUTING_KEY = "product.price.updated.routing.key";

    @RabbitListener(queues = PRODUCT_PRICE_UPDATED_DLQ_QUEUE)
    public void onMessage(ProductPriceUpdatedEvent productPriceUpdatedEvent) {
        this.messageProducer.publish(PRODUCT_UPDATED_EXCHANGE, PRODUCT_PRICE_UPDATED_ROUTING_KEY, productPriceUpdatedEvent);
    }
}
