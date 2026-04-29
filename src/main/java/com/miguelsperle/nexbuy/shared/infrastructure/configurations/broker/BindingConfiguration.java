package com.miguelsperle.nexbuy.shared.infrastructure.configurations.broker;

import com.miguelsperle.nexbuy.shared.infrastructure.configurations.broker.annotations.exchanges.*;
import com.miguelsperle.nexbuy.shared.infrastructure.configurations.broker.annotations.queues.*;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfiguration {
    @Bean
    public Binding userCreatedBinding(
            @Qualifier("userCreatedQueue") Queue queue,
            @Qualifier("userCreatedExchange") FanoutExchange fanoutExchange
    ) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public Binding userCreatedCartBinding(
            @Qualifier("userCreatedCartQueue") Queue queue,
            @Qualifier("userCreatedExchange") FanoutExchange fanoutExchange
    ) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public Binding userCreatedDlqBinding(
            @Qualifier("userCreatedDlqQueue") Queue queue,
            @Qualifier("userCreatedDlqExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("user.created.dlq.routing.key");
    }

    @Bean
    public Binding userCreatedCartDlqBinding(
            @Qualifier("userCreatedCartDlqQueue") Queue queue,
            @Qualifier("userCreatedCartDlqExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("user.created.cart.dlq.routing.key");
    }

    @Bean
    public Binding userCodeCreatedBinding(
            @Qualifier("userCodeCreatedQueue") Queue queue,
            @Qualifier("userCodeCreatedExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("user.code.created.routing.key");
    }

    @Bean
    public Binding userCodeCreatedDlqBinding(
            @Qualifier("userCodeCreatedDlqQueue") Queue queue,
            @Qualifier("userCodeCreatedDlqExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("user.code.created.dlq.routing.key");
    }

    @Bean
    public Binding productRegisteredBinding(
            @Qualifier("productRegisteredQueue") Queue queue,
            @Qualifier("productRegisteredExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("product.registered.routing.key");
    }

    @Bean
    public Binding productRegisteredDlqBinding(
            @Qualifier("productRegisteredDlqQueue") Queue queue,
            @Qualifier("productRegisteredDlqExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("product.registered.dlq.routing.key");
    }

    @Bean
    public Binding productSkuUpdatedBinding(
            @Qualifier("productSkuUpdatedQueue") Queue queue,
            @Qualifier("productUpdatedExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("product.sku.updated.routing.key");
    }

    @Bean
    public Binding productSkuUpdatedDlqBinding(
            @Qualifier("productSkuUpdatedDlqQueue") Queue queue,
            @Qualifier("productUpdatedDlqExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("product.sku.updated.dlq.routing.key");
    }

    @Bean
    public Binding productPriceUpdatedBinding(
            @Qualifier("productPriceUpdatedQueue") Queue queue,
            @Qualifier("productUpdatedExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("product.price.updated.routing.key");
    }

    @Bean
    public Binding productPriceUpdatedDlqBinding(
            @Qualifier("productPriceUpdatedDlqQueue") Queue queue,
            @Qualifier("productUpdatedDlqExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("product.price.updated.dlq.routing.key");
    }

    @Bean
    public Binding updatePaymentStatusBinding(
            @Qualifier("updatePaymentStatusQueue") Queue queue,
            @Qualifier("updatePaymentStatusExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("update.payment.status.routing.key");
    }

    @Bean
    public Binding updatePaymentStatusDlqBinding(
            @Qualifier("updatePaymentStatusDlqQueue") Queue queue,
            @Qualifier("updatePaymentStatusDlqExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("update.payment.status.dlq.routing.key");
    }

    @Bean
    public Binding paymentStatusUpdatedBinding(
            @Qualifier("paymentStatusUpdatedQueue") Queue queue,
            @Qualifier("paymentStatusUpdatedExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("payment.status.updated.routing.key");
    }

    @Bean
    public Binding paymentStatusUpdatedDlqBinding(
            @Qualifier("paymentStatusUpdatedDlqQueue") Queue queue,
            @Qualifier("paymentStatusUpdatedDlqExchange") DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("payment.status.updated.dlq.routing.key");
    }
}
