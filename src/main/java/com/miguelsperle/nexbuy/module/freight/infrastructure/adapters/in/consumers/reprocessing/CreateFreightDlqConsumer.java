package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.consumers.reprocessing;

import com.miguelsperle.nexbuy.shared.application.commands.CreateFreightCommand;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateFreightDlqConsumer {
    private final MessageProducer messageProducer;

    private static final String CREATE_FREIGHT_DLQ_QUEUE = "create.freight.dlq.queue";
    private static final String CREATE_FREIGHT_EXCHANGE = "create.freight.exchange";
    private static final String CREATE_FREIGHT_ROUTING_KEY = "create.freight.routing.key";

    @RabbitListener(queues = CREATE_FREIGHT_DLQ_QUEUE)
    public void onMessage(CreateFreightCommand createFreightCommand) {
        this.messageProducer.publish(CREATE_FREIGHT_EXCHANGE, CREATE_FREIGHT_ROUTING_KEY, createFreightCommand);
    }
}
