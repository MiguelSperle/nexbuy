package com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker;

import com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker.annotations.exchanges.*;
import com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker.annotations.queues.*;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfiguration {
    @Bean
    public Binding userCreatedBinding(
            @Value("${spring.rabbitmq.routing-keys.user-created") String routingKey,
            @UserCreatedQueue Queue queue,
            @UserCreatedExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    public Binding userCreatedDlqBinding(
            @Value("${spring.rabbitmq.routing-keys.user-created-dlq") String routingKey,
            @UserCreatedDlqQueue Queue queue,
            @UserCreatedDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    public Binding userCodeCreatedBinding(
            @Value("${spring.rabbitmq.routing-keys.user-code-created}") String routingKey,
            @UserCodeCreatedQueue Queue queue,
            @UserCodeCreatedExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    public Binding userCodeCreatedDlqBinding(
            @Value("${spring.rabbitmq.routing-keys.user-code-created-dlq}") String routingKey,
            @UserCodeCreatedDlqQueue Queue queue,
            @UserCodeCreatedDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    public Binding productRegisteredBinding(
            @Value("${spring.rabbitmq.routing-keys.product-registered}") String routingKey,
            @ProductRegisteredQueue Queue queue,
            @ProductRegisteredExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    public Binding productRegisteredDlqBinding(
            @Value("${spring.rabbitmq.routing-keys.product-registered-dlq}") String routingKey,
            @ProductRegisteredDlqQueue Queue queue,
            @ProductRegisteredDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    public Binding productSkuUpdatedBinding(
            @Value("${spring.rabbitmq.routing-keys.product-sku-updated}") String routingKey,
            @ProductSkuUpdatedQueue Queue queue,
            @ProductUpdatedExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    public Binding productSkuUpdatedDlqBinding(
            @Value("${spring.rabbitmq.routing-keys.product-sku-updated-dlq}") String routingKey,
            @ProductSkuUpdatedDlqQueue Queue queue,
            @ProductUpdatedDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    public Binding productPriceUpdatedBinding(
            @Value("${spring.rabbitmq.routing-keys.product-price-updated}") String routingKey,
            @ProductPriceUpdatedQueue Queue queue,
            @ProductUpdatedExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    public Binding productPriceUpdatedDlqBinding(
            @Value("${spring.rabbitmq.routing-keys.product-price-updated-dlq}") String routingKey,
            @ProductPriceUpdatedDlqQueue Queue queue,
            @ProductUpdatedDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }
}
