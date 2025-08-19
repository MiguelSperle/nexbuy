package com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker;

import com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker.annotations.ProductRegisteredQueueProperties;
import com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker.annotations.ProductSkuUpdatedQueueProperties;
import com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker.annotations.UserCodeCreatedQueueProperties;
import com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker.annotations.UserCreatedQueueProperties;
import com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker.properties.RabbitQueueProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfiguration {
    @Bean
    public Declarables userCreatedDeclarables(@UserCreatedQueueProperties RabbitQueueProperties properties) {
        final DirectExchange directExchange = new DirectExchange(properties.getExchange());

        final Queue queue = QueueBuilder.durable(properties.getQueue()).deadLetterExchange(properties.getDlq().getExchange())
                .deadLetterRoutingKey(properties.getDlq().getRoutingKey()).build();

        final Binding binding = BindingBuilder.bind(queue).to(directExchange).with(properties.getRoutingKey());

        final DirectExchange dlqDirectExchange = new DirectExchange(properties.getDlq().getExchange());

        final Queue dlqQueue = new Queue(properties.getDlq().getQueue());

        final Binding dlqBinding = BindingBuilder.bind(dlqQueue).to(dlqDirectExchange).with(properties.getDlq().getRoutingKey());

        return new Declarables(directExchange, queue, binding, dlqDirectExchange, dlqQueue, dlqBinding);
    }

    @Bean
    public Declarables userCodeCreatedDeclarables(@UserCodeCreatedQueueProperties RabbitQueueProperties properties) {
        final DirectExchange directExchange = new DirectExchange(properties.getExchange());

        final Queue queue = QueueBuilder.durable(properties.getQueue()).deadLetterExchange(properties.getDlq().getExchange())
                .deadLetterRoutingKey(properties.getDlq().getRoutingKey()).build();

        final Binding binding = BindingBuilder.bind(queue).to(directExchange).with(properties.getRoutingKey());

        final DirectExchange dlqDirectExchange = new DirectExchange(properties.getDlq().getExchange());

        final Queue dlqQueue = new Queue(properties.getDlq().getQueue());

        final Binding dlqBinding = BindingBuilder.bind(dlqQueue).to(dlqDirectExchange).with(properties.getDlq().getRoutingKey());

        return new Declarables(directExchange, queue, binding, dlqDirectExchange, dlqQueue, dlqBinding);
    }

    @Bean
    public Declarables productRegisteredDeclarables(@ProductRegisteredQueueProperties RabbitQueueProperties properties) {
        final DirectExchange directExchange = new DirectExchange(properties.getExchange());

        final Queue queue = QueueBuilder.durable(properties.getQueue()).deadLetterExchange(properties.getDlq().getExchange())
                .deadLetterRoutingKey(properties.getDlq().getRoutingKey()).build();

        final Binding binding = BindingBuilder.bind(queue).to(directExchange).with(properties.getRoutingKey());

        final DirectExchange dlqDirectExchange = new DirectExchange(properties.getDlq().getExchange());

        final Queue dlqQueue = new Queue(properties.getDlq().getQueue());

        final Binding dlqBinding = BindingBuilder.bind(dlqQueue).to(dlqDirectExchange).with(properties.getDlq().getRoutingKey());

        return new Declarables(directExchange, queue, binding, dlqDirectExchange, dlqQueue, dlqBinding);
    }

    @Bean
    public Declarables productSkuUpdatedDeclarables(@ProductSkuUpdatedQueueProperties RabbitQueueProperties properties) {
        final DirectExchange directExchange = new DirectExchange(properties.getExchange());

        final Queue queue = QueueBuilder.durable(properties.getQueue()).deadLetterExchange(properties.getDlq().getExchange())
                .deadLetterRoutingKey(properties.getDlq().getRoutingKey()).build();

        final Binding binding = BindingBuilder.bind(queue).to(directExchange).with(properties.getRoutingKey());

        final DirectExchange dlqDirectExchange = new DirectExchange(properties.getDlq().getExchange());

        final Queue dlqQueue = new Queue(properties.getDlq().getQueue());

        final Binding dlqBinding = BindingBuilder.bind(dlqQueue).to(dlqDirectExchange).with(properties.getDlq().getRoutingKey());

        return new Declarables(directExchange, queue, binding, dlqDirectExchange, dlqQueue, dlqBinding);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }
}
