package com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfiguration {
    @Bean
    public FanoutExchange userCreatedExchange() {
        return new FanoutExchange("user.created.exchange");
    }

    @Bean
    public DirectExchange userCreatedDlqExchange() {
        return new DirectExchange("user.created.dlq.exchange");
    }

    @Bean
    public DirectExchange userCreatedCartDlqExchange() {
        return new DirectExchange("user.created.cart.dlq.exchange");
    }

    @Bean
    public DirectExchange userCodeCreatedExchange() {
        return new DirectExchange("user.code.created.exchange");
    }

    @Bean
    public DirectExchange userCodeCreatedDlqExchange() {
        return new DirectExchange("user.code.created.dlq.exchange");
    }

    @Bean
    public DirectExchange productRegisteredExchange() {
        return new DirectExchange("product.registered.exchange");
    }

    @Bean
    public DirectExchange productRegisteredDlqExchange() {
        return new DirectExchange("product.registered.dlq.exchange");
    }

    @Bean
    public DirectExchange productUpdatedExchange() {
        return new DirectExchange("product.updated.exchange");
    }

    @Bean
    public DirectExchange productUpdatedDlqExchange() {
        return new DirectExchange("product.updated.dlq.exchange");
    }

    @Bean
    public DirectExchange createFreightExchange() {
        return new DirectExchange("create.freight.exchange");
    }

    @Bean
    public DirectExchange createFreightDlqExchange() {
        return new DirectExchange("create.freight.dlq.exchange");
    }

    @Bean
    public DirectExchange updatePaymentStatusExchange() {
        return new DirectExchange("update.payment.status.exchange");
    }

    @Bean
    public DirectExchange updatePaymentStatusDlqExchange() {
        return new DirectExchange("update.payment.status.dlq.exchange");
    }

    @Bean
    public DirectExchange paymentStatusUpdatedExchange() {
        return new DirectExchange("payment.status.updated.exchange");
    }

    @Bean
    public DirectExchange paymentStatusUpdatedDlqExchange() {
        return new DirectExchange("payment.status.updated.dlq.exchange");
    }
}
