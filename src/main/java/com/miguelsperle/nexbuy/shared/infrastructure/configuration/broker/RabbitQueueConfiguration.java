package com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker;

import com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker.properties.RabbitQueueProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfiguration {
    @Bean
    public Declarables userCreatedDeclarables(
            @Qualifier("userCreatedQueueProperties") RabbitQueueProperties props
    ) {
        return this.createQueueWithDlq(props);
    }

    @Bean
    public Declarables userCodeCreatedDeclarables(
            @Qualifier("userCodeCreatedQueueProperties") RabbitQueueProperties props
    ) {
        return this.createQueueWithDlq(props);
    }

    private Declarables createQueueWithDlq(RabbitQueueProperties props) {
        final DirectExchange mainExchange = new DirectExchange(props.getExchange());

        final Queue mainQueue = QueueBuilder.durable(props.getQueue()).deadLetterExchange(props.getDlq().getExchange())
                .deadLetterRoutingKey(props.getDlq().getRoutingKey()).build();

        final Binding mainBinding = BindingBuilder.bind(mainQueue).to(mainExchange).with(props.getRoutingKey());

        final DirectExchange dlqDirectExchange = new DirectExchange(props.getDlq().getExchange());

        final Queue dlqQueue = new Queue(props.getDlq().getQueue());

        final Binding dlqBinding = BindingBuilder.bind(dlqQueue).to(dlqDirectExchange).with(props.getDlq().getRoutingKey());

        return new Declarables(mainExchange, mainQueue, mainBinding, dlqDirectExchange, dlqQueue, dlqBinding);
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
