package com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfiguration {
    @Bean
    public DirectExchange userCreatedExchange(@Value("${spring.rabbitmq.exchanges.user-created}") String exchange) {
        return new DirectExchange(exchange);
    }

    @Bean
    public DirectExchange userCreatedDlqExchange(@Value("${spring.rabbitmq.exchanges.user-created-dlq}") String exchange) {
        return new DirectExchange(exchange);
    }

    @Bean
    public DirectExchange userCodeCreatedExchange(@Value("${spring.rabbitmq.exchanges.user-code-created}") String exchange) {
        return new DirectExchange(exchange);
    }

    @Bean
    public DirectExchange userCodeCreatedDlqExchange(@Value("${spring.rabbitmq.exchanges.user-code-created-dlq}") String exchange) {
        return new DirectExchange(exchange);
    }

    @Bean
    public DirectExchange productRegisteredExchange(@Value("${spring.rabbitmq.exchanges.product-registered}") String exchange) {
        return new DirectExchange(exchange);
    }

    @Bean
    public DirectExchange productRegisteredDlqExchange(@Value("${spring.rabbitmq.exchanges.product-registered-dlq}") String exchange) {
        return new DirectExchange(exchange);
    }

    @Bean
    public DirectExchange productUpdatedExchange(@Value("${spring.rabbitmq.exchanges.product-updated}") String exchange) {
        return new DirectExchange(exchange);
    }

    @Bean
    public DirectExchange productUpdatedDlqExchange(@Value("${spring.rabbitmq.exchanges.product-updated-dlq}") String exchange) {
        return new DirectExchange(exchange);
    }
}
