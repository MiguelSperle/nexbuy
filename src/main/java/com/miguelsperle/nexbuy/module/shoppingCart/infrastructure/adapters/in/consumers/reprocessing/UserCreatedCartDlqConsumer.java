package com.miguelsperle.nexbuy.module.shoppingCart.infrastructure.adapters.in.consumers.reprocessing;

import com.miguelsperle.nexbuy.shared.domain.events.UserCreatedEvent;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedCartDlqConsumer {
    private final MessageProducer messageProducer;

    private static final String USER_CREATED_CART_DLQ_QUEUE = "user.created.cart.dlq.queue";
    private static final String USER_CREATED_CART_QUEUE = "user.created.cart.queue";

    // DEFAULT EXCHANGE because I'm leading with Fanout

    @RabbitListener(queues = USER_CREATED_CART_DLQ_QUEUE)
    public void onMessage(UserCreatedEvent userCreatedEvent) {
        this.messageProducer.publish("", USER_CREATED_CART_QUEUE, userCreatedEvent);
    }
}
