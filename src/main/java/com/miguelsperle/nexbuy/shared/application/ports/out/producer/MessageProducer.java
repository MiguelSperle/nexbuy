package com.miguelsperle.nexbuy.shared.application.ports.out.producer;

public interface MessageProducer {
    void publish(String exchange, String routingKey, Object message);
}
