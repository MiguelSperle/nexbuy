package com.miguelsperle.nexbuy.module.user.infrastructure.listeners.reprocessing;

import com.miguelsperle.nexbuy.module.user.domain.events.UserCreatedEvent;
import com.miguelsperle.nexbuy.shared.application.abstractions.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedDlqListener {
    private final MessageProducer messageProducer;

    private static final String USER_CREATED_DLQ_QUEUE = "user.created.dlq.queue";
    private static final String USER_CREATED_QUEUE = "user.created.queue";

    // DEFAULT EXCHANGE because I'm leading with Fanout

    @RabbitListener(queues = USER_CREATED_DLQ_QUEUE)
    public void onMessage(UserCreatedEvent userCreatedEvent) {
        this.messageProducer.publish("", USER_CREATED_QUEUE, userCreatedEvent);
    }
}
