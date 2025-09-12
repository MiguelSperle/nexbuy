package com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {
    @Bean
    public Queue userCreatedQueue() {
        return QueueBuilder.durable("user.created.queue")
                .deadLetterExchange("user.created.dlq.exchange")
                .deadLetterRoutingKey("user.created.dlq.routing.key")
                .build();
    }

    @Bean
    public Queue userCreatedDlqQueue() {
        return new Queue("user.created.dlq.queue");
    }

    @Bean
    public Queue userCreatedCartQueue() {
        return QueueBuilder.durable("user.created.cart.queue")
                .deadLetterExchange("user.created.cart.dlq.exchange")
                .deadLetterRoutingKey("user.created.cart.dlq.routing.key")
                .build();
    }

    @Bean
    public Queue userCreatedCartDlqQueue() {
        return new Queue("user.created.cart.dlq.queue");
    }

    @Bean
    public Queue userCodeCreatedQueue() {
        return QueueBuilder.durable("user.code.created.queue")
                .deadLetterExchange("user.code.created.dlq.exchange")
                .deadLetterRoutingKey("user.code.created.dlq.routing.key")
                .build();
    }

    @Bean
    public Queue userCodeCreatedDlqQueue() {
        return new Queue("user.code.created.dlq.queue");
    }

    @Bean
    public Queue productRegisteredQueue() {
        return QueueBuilder.durable("product.registered.queue")
                .deadLetterExchange("product.registered.dlq.exchange")
                .deadLetterRoutingKey("product.registered.dlq.routing.key")
                .build();
    }

    @Bean
    public Queue productRegisteredDlqQueue() {
        return new Queue("product.registered.dlq.queue");
    }

    @Bean
    public Queue productSkuUpdatedQueue() {
        return QueueBuilder.durable("product.sku.updated.queue")
                .deadLetterExchange("product.updated.dlq.exchange")
                .deadLetterRoutingKey("product.sku.updated.dlq.routing.key")
                .build();
    }

    @Bean
    public Queue productSkuUpdatedDlqQueue() {
        return new Queue("product.sku.updated.dlq.queue");
    }

    @Bean
    public Queue productPriceUpdatedQueue() {
        return QueueBuilder.durable("product.price.updated.queue")
                .deadLetterExchange("product.updated.dlq.exchange")
                .deadLetterRoutingKey("product.price.updated.dlq.routing.key")
                .build();
    }

    @Bean
    public Queue productPriceUpdatedDlqQueue() {
        return new Queue("product.price.updated.dlq.queue");
    }

    @Bean
    public Queue createFreightQueue() {
        return QueueBuilder.durable("create.freight.queue")
                .deadLetterExchange("create.freight.dlq.exchange")
                .deadLetterRoutingKey("create.freight.dlq.routing.key")
                .build();
    }

    @Bean
    public Queue createFreightDlqQueue() {
        return new Queue("create.freight.dlq.queue");
    }

    @Bean
    public Queue updatePaymentStatusQueue() {
        return QueueBuilder.durable("update.payment.status.queue")
                .deadLetterExchange("update.payment.status.dlq.exchange")
                .deadLetterRoutingKey("update.payment.status.dlq.routing.key")
                .build();
    }

    @Bean
    public Queue updatePaymentStatusDlqQueue() {
        return new Queue("update.payment.status.dlq.queue");
    }

    @Bean
    public Queue paymentStatusUpdatedQueue() {
        return QueueBuilder.durable("payment.status.updated.queue")
                .deadLetterExchange("payment.status.updated.dlq.exchange")
                .deadLetterRoutingKey("payment.status.updated.dlq.routing.key")
                .build();
    }

    @Bean
    public Queue paymentStatusUpdatedDlqQueue() {
        return new Queue("payment.status.updated.dlq.queue");
    }
}
