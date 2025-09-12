package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.consumers.reprocessing;

import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.domain.events.PaymentStatusUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStatusUpdatedDlqConsumer {
    private final MessageProducer messageProducer;

    private static final String PAYMENT_STATUS_UPDATED_DLQ_QUEUE = "payment.status.updated.dlq.queue";
    private static final String PAYMENT_STATUS_UPDATED_EXCHANGE = "payment.status.updated.exchange";
    private static final String PAYMENT_STATUS_UPDATED_ROUTING_KEY = "payment.status.updated.routing.key";

    @RabbitListener(queues = PAYMENT_STATUS_UPDATED_DLQ_QUEUE)
    public void onMessage(PaymentStatusUpdatedEvent paymentStatusUpdatedEvent) {
        this.messageProducer.publish(PAYMENT_STATUS_UPDATED_EXCHANGE, PAYMENT_STATUS_UPDATED_ROUTING_KEY, paymentStatusUpdatedEvent);
    }
}
