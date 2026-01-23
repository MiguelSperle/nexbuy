package com.miguelsperle.nexbuy.common.application.ports.out.producer;

public interface MessageProducer {
    void publish(String exchange, String routingKey, Object message);
}
