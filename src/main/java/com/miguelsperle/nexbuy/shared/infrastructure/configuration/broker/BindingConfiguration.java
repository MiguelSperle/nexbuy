package com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker;

import com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker.annotations.exchanges.*;
import com.miguelsperle.nexbuy.shared.infrastructure.configuration.broker.annotations.queues.*;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfiguration {
    @Bean
    public Binding userCreatedBinding(
            @UserCreatedQueue Queue queue,
            @UserCreatedExchange FanoutExchange fanoutExchange
    ) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public Binding userCreatedCartBinding(
            @UserCreatedCartQueue Queue queue,
            @UserCreatedExchange FanoutExchange fanoutExchange
    ) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public Binding userCreatedDlqBinding(
            @UserCreatedDlqQueue Queue queue,
            @UserCreatedDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("user.created.dlq.routing.key");
    }

    @Bean
    public Binding userCreatedCartDlqBinding(
            @UserCreatedCartDlqQueue Queue queue,
            @UserCreatedCartDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("user.created.cart.dlq.routing.key");
    }

    @Bean
    public Binding userCodeCreatedBinding(
            @UserCodeCreatedQueue Queue queue,
            @UserCodeCreatedExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("user.code.created.routing.key");
    }

    @Bean
    public Binding userCodeCreatedDlqBinding(
            @UserCodeCreatedDlqQueue Queue queue,
            @UserCodeCreatedDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("user.code.created.dlq.routing.key");
    }

    @Bean
    public Binding productRegisteredBinding(
            @ProductRegisteredQueue Queue queue,
            @ProductRegisteredExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("product.registered.routing.key");
    }

    @Bean
    public Binding productRegisteredDlqBinding(
            @ProductRegisteredDlqQueue Queue queue,
            @ProductRegisteredDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("product.registered.dlq.routing.key");
    }

    @Bean
    public Binding productSkuUpdatedBinding(
            @ProductSkuUpdatedQueue Queue queue,
            @ProductUpdatedExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("product.sku.updated.routing.key");
    }

    @Bean
    public Binding productSkuUpdatedDlqBinding(
            @ProductSkuUpdatedDlqQueue Queue queue,
            @ProductUpdatedDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("product.sku.updated.dlq.routing.key");
    }

    @Bean
    public Binding productPriceUpdatedBinding(
            @ProductPriceUpdatedQueue Queue queue,
            @ProductUpdatedExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("product.price.updated.routing.key");
    }

    @Bean
    public Binding productPriceUpdatedDlqBinding(
            @ProductPriceUpdatedDlqQueue Queue queue,
            @ProductUpdatedDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("product.price.updated.dlq.routing.key");
    }

    @Bean
    public Binding createFreightBinding(
            @CreateFreightQueue Queue queue,
            @CreateFreightExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("create.freight.routing.key");
    }

    @Bean
    public Binding createFreightDlqBinding(
            @CreateFreightDlqQueue Queue queue,
            @CreateFreightDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("create.freight.dlq.routing.key");
    }

    @Bean
    public Binding createPaymentBinding(
            @CreatePaymentQueue Queue queue,
            @CreatePaymentExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("create.payment.routing.key");
    }

    @Bean
    public Binding createPaymentDlqBinding(
            @CreatePaymentDlqQueue Queue queue,
            @CreatePaymentDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("create.payment.dlq.routing.key");
    }
}
