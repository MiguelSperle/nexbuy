package com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {
    @Bean
    public Queue userCreatedQueue(
            @Value("${spring.rabbitmq.queues.user-created}") String queue,
            @Value("${spring.rabbitmq.exchanges.user-created-dlq}") String exchange,
            @Value("${spring.rabbitmq.routing-keys.user-created-dlq}") String routingKey
    ) {
        return QueueBuilder.durable(queue).deadLetterExchange(exchange).deadLetterRoutingKey(routingKey).build();
    }

    @Bean
    public Queue userCreatedDlqQueue(@Value("${spring.rabbitmq.queues.user-created-dlq}") String queue) {
        return new Queue(queue);
    }

    @Bean
    public Queue userCodeCreatedQueue(
            @Value("${spring.rabbitmq.queues.user-code-created}") String queue,
            @Value("${spring.rabbitmq.exchanges.user-code-created-dlq}") String exchange,
            @Value("${spring.rabbitmq.routing-keys.user-code-created-dlq}") String routingKey
    ) {
        return QueueBuilder.durable(queue).deadLetterExchange(exchange).deadLetterRoutingKey(routingKey).build();
    }

    @Bean
    public Queue userCodeCreatedDlqQueue(@Value("${spring.rabbitmq.queues.user-code-created-dlq}") String queue) {
        return new Queue(queue);
    }

    @Bean
    public Queue productRegisteredQueue(
            @Value("${spring.rabbitmq.queues.product-registered}") String queue,
            @Value("${spring.rabbitmq.exchanges.product-registered-dlq}") String exchange,
            @Value("${spring.rabbitmq.routing-keys.product-registered-dlq}") String routingKey
    ) {
        return QueueBuilder.durable(queue).deadLetterExchange(exchange).deadLetterRoutingKey(routingKey).build();
    }

    @Bean
    public Queue productRegisteredDlqQueue(@Value("${spring.rabbitmq.queues.product-registered-dlq}") String queue) {
        return new Queue(queue);
    }

    @Bean
    public Queue productSkuUpdatedQueue(
            @Value("${spring.rabbitmq.queues.product-sku-updated}") String queue,
            @Value("${spring.rabbitmq.exchanges.product-updated-dlq}") String exchange,
            @Value("${spring.rabbitmq.routing-keys.product-sku-updated-dlq}") String routingKey
    ) {
        return QueueBuilder.durable(queue).deadLetterExchange(exchange).deadLetterRoutingKey(routingKey).build();
    }

    @Bean
    public Queue productSkuUpdatedDlqQueue(@Value("${spring.rabbitmq.queues.product-sku-updated-dlq}") String queue) {
        return new Queue(queue);
    }

    @Bean
    public Queue productPriceUpdatedQueue(
            @Value("${spring.rabbitmq.queues.product-price-updated}") String queue,
            @Value("${spring.rabbitmq.exchanges.product-updated-dlq}") String exchange,
            @Value("${spring.rabbitmq.routing-keys.product-price-updated-dlq}") String routingKey
    ) {
        return QueueBuilder.durable(queue).deadLetterExchange(exchange).deadLetterRoutingKey(routingKey).build();
    }

    @Bean
    public Queue productPriceUpdatedDlqQueue(@Value("${spring.rabbitmq.queues.product-price-updated-dlq}") String queue) {
        return new Queue(queue);
    }
}
