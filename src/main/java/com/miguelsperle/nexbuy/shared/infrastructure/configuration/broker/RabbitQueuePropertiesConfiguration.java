package com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker;

import com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker.properties.RabbitQueueProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueuePropertiesConfiguration {
    private static final String PREFIX = "spring.rabbitmq.queues";

    @Bean
    @ConfigurationProperties(PREFIX + ".user-created")
    public RabbitQueueProperties userCreatedQueueProperties() {
        return new RabbitQueueProperties();
    }

    @Bean
    @ConfigurationProperties(PREFIX + ".user-code-created")
    public RabbitQueueProperties userCodeCreatedQueueProperties() {
        return new RabbitQueueProperties();
    }
}
