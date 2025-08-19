package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.in.consumers;

import com.miguelsperle.nexbuy.shared.domain.events.ProductRegisteredEvent;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRegisteredDlqConsumer {
    private final MessageProducer messageProducer;

    private static final String PRODUCT_REGISTERED_DLQ_QUEUE = "product.registered.dlq.queue";
    private static final String PRODUCT_REGISTERED_EXCHANGE = "product.registered.exchange";
    private static final String PRODUCT_REGISTERED_ROUTING_KEY = "product.registered.routing.key";

    @RabbitListener(queues = PRODUCT_REGISTERED_DLQ_QUEUE)
    public void onMessage(ProductRegisteredEvent productRegisteredEvent) {
        this.messageProducer.publish(PRODUCT_REGISTERED_EXCHANGE, PRODUCT_REGISTERED_ROUTING_KEY, productRegisteredEvent);
    }
}
