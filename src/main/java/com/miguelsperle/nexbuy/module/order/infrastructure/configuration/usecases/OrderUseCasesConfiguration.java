package com.miguelsperle.nexbuy.module.order.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.order.application.ports.in.usecases.CreateOrderUseCase;
import com.miguelsperle.nexbuy.module.order.application.ports.in.usecases.GetOrderUseCase;
import com.miguelsperle.nexbuy.module.order.application.ports.in.usecases.GetOrdersUseCase;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderDeliveryRepository;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderItemRepository;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderRepository;
import com.miguelsperle.nexbuy.module.order.application.usecases.CreateOrderUseCaseImpl;
import com.miguelsperle.nexbuy.module.order.application.usecases.GetOrderUseCaseImpl;
import com.miguelsperle.nexbuy.module.order.application.usecases.GetOrdersUseCaseImpl;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.CodeProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
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
            TransactionExecutor transactionExecutor,
            MessageProducer messageProducer
    ) {
        return new CreateOrderUseCaseImpl(
                orderRepository,
                orderItemRepository,
                orderDeliveryRepository,
                securityContextService,
                codeProvider,
                transactionExecutor,
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
