package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.consumers.reprocessing;

import com.miguelsperle.nexbuy.module.payment.application.commands.UpdatePaymentStatusCommand;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePaymentStatusDlqConsumer {
    private final MessageProducer messageProducer;

    private static final String UPDATE_PAYMENT_STATUS_DLQ_QUEUE = "update.payment.status.dlq.queue";
    private static final String UPDATE_PAYMENT_STATUS_EXCHANGE = "update.payment.status.exchange";
    private static final String UPDATE_PAYMENT_STATUS_ROUTING_KEY = "update.payment.status.routing.key";

    @RabbitListener(queues = UPDATE_PAYMENT_STATUS_DLQ_QUEUE)
    public void onMessage(UpdatePaymentStatusCommand updatePaymentStatusCommand) {
        this.messageProducer.publish(UPDATE_PAYMENT_STATUS_EXCHANGE, UPDATE_PAYMENT_STATUS_ROUTING_KEY, updatePaymentStatusCommand);
    }
}
