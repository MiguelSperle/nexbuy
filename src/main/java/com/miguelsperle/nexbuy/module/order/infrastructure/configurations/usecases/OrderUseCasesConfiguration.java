package com.miguelsperle.nexbuy.module.order.infrastructure.configurations.usecases;

import com.miguelsperle.nexbuy.module.order.application.abstractions.usecases.CreateOrderUseCase;
import com.miguelsperle.nexbuy.module.order.application.abstractions.usecases.GetOrderUseCase;
import com.miguelsperle.nexbuy.module.order.application.abstractions.usecases.GetOrdersUseCase;
import com.miguelsperle.nexbuy.module.order.application.abstractions.repositories.OrderDeliveryRepository;
import com.miguelsperle.nexbuy.module.order.application.abstractions.repositories.OrderItemRepository;
import com.miguelsperle.nexbuy.module.order.application.abstractions.repositories.OrderRepository;
import com.miguelsperle.nexbuy.module.order.application.usecases.CreateOrderUseCaseImpl;
import com.miguelsperle.nexbuy.module.order.application.usecases.GetOrderUseCaseImpl;
import com.miguelsperle.nexbuy.module.order.application.usecases.GetOrdersUseCaseImpl;
import com.miguelsperle.nexbuy.shared.application.abstractions.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.abstractions.providers.CodeProvider;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.application.abstractions.wrapper.TransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderUseCasesConfiguration {
    @Bean
    public CreateOrderUseCase createOrderUseCase(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            OrderDeliveryRepository orderDeliveryRepository,
            SecurityContextService securityContextService,
            CodeProvider codeProvider,
            TransactionManager transactionManager,
            MessageProducer messageProducer
    ) {
        return new CreateOrderUseCaseImpl(
                orderRepository,
                orderItemRepository,
                orderDeliveryRepository,
                securityContextService,
                codeProvider,
                transactionManager,
                messageProducer
        );
    }

    @Bean
    public GetOrdersUseCase getOrdersUseCase(
            OrderRepository orderRepository,
            SecurityContextService securityContextService
    ) {
        return new GetOrdersUseCaseImpl(
                orderRepository,
                securityContextService
        );
    }

    @Bean
    public GetOrderUseCase getOrderUseCase(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            OrderDeliveryRepository orderDeliveryRepository
    ) {
        return new GetOrderUseCaseImpl(
                orderRepository,
                orderItemRepository,
                orderDeliveryRepository
        );
    }
}
