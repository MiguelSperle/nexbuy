package com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker.properties;

import lombok.Data;

@Data
public class RabbitQueueProperties {
    private String queue;
    private String exchange;
    private String routingKey;
    private Dlq dlq;

    @Data
    public static class Dlq {
        private String queue;
        private String exchange;
        private String routingKey;
    }
}
