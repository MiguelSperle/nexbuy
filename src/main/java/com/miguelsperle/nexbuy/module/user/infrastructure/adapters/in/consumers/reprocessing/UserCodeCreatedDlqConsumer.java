package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.consumers.reprocessing;

import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCodeCreatedDlqConsumer {
    private final MessageProducer messageProducer;

    private static final String USER_CODE_CREATED_DLQ_QUEUE = "user.code.created.dlq.queue";
    private static final String USER_CODE_CREATED_EXCHANGE = "user.code.created.exchange";
    private static final String USER_CODE_CREATED_ROUTING_KEY = "user.code.created.routing.key";

    @RabbitListener(queues = USER_CODE_CREATED_DLQ_QUEUE)
    public void onMessage(UserCodeCreatedEvent userCodeCreatedEvent) {
        this.messageProducer.publish(USER_CODE_CREATED_EXCHANGE, USER_CODE_CREATED_ROUTING_KEY, userCodeCreatedEvent);
    }
}
