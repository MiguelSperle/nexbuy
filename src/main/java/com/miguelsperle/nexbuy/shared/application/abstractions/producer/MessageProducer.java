package com.miguelsperle.nexbuy.shared.application.abstractions.producer;

public interface MessageProducer {
    void publish(String exchange, String routingKey, Object message);
}
