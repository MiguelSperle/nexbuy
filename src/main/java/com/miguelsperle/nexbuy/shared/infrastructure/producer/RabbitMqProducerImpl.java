package com.miguelsperle.nexbuy.shared.infrastructure.producer;

import com.miguelsperle.nexbuy.shared.application.abstractions.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMqProducerImpl implements MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(String exchange, String routingKey, Object message) {
        this.rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
