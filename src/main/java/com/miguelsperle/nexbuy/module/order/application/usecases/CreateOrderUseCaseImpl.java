package com.miguelsperle.nexbuy.module.order.application.usecases;

import com.miguelsperle.nexbuy.module.freight.application.abstractions.usecases.CreateFreightUseCase;
import com.miguelsperle.nexbuy.module.order.application.abstractions.usecases.CreateOrderUseCase;
import com.miguelsperle.nexbuy.module.order.application.abstractions.repositories.OrderDeliveryRepository;
import com.miguelsperle.nexbuy.module.order.application.abstractions.repositories.OrderItemRepository;
import com.miguelsperle.nexbuy.module.order.application.abstractions.repositories.OrderRepository;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.CreateOrderUseCaseInput;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderDelivery;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderItem;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.CreateFreightUseCaseInput;
import com.miguelsperle.nexbuy.shared.application.abstractions.providers.CodeProvider;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityService;
import com.miguelsperle.nexbuy.shared.application.abstractions.wrapper.TransactionManager;

import java.util.List;

public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderDeliveryRepository orderDeliveryRepository;
    private final SecurityService securityService;
    private final CodeProvider codeProvider;
    private final TransactionManager transactionManager;
    private final CreateFreightUseCase createFreightUseCase;

    private final static int CODE_LENGTH = 12;
    private final static String NUMERIC_CHARACTERS = "0123456789";

    public CreateOrderUseCaseImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            OrderDeliveryRepository orderDeliveryRepository,
            SecurityService securityService,
            CodeProvider codeProvider,
            TransactionManager transactionManager,
            CreateFreightUseCase createFreightUseCase
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderDeliveryRepository = orderDeliveryRepository;
        this.securityService = securityService;
        this.codeProvider = codeProvider;
        this.transactionManager = transactionManager;
        this.createFreightUseCase = createFreightUseCase;
    }

    @Override
    public void execute(CreateOrderUseCaseInput createOrderUseCaseInput) {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final String codeGenerated = this.codeProvider.generateCode(CODE_LENGTH, NUMERIC_CHARACTERS);

        final Order newOrder = Order.newOrder(authenticatedUserId, codeGenerated, createOrderUseCaseInput.totalAmount());

        this.transactionManager.runTransaction(() -> {
            final Order orderSaved = this.saveOrder(newOrder);

            final List<OrderItem> newOrderItems = createOrderUseCaseInput.orderItemsComplementInput().stream()
                    .map(orderItem -> OrderItem.newOrderItem(
                            orderSaved.getId(),
                            orderItem.productId(),
                            orderItem.quantity(),
                            orderItem.unitPrice()
                    )).toList();

            this.saveOrderItems(newOrderItems);

            final OrderDelivery newOrderDelivery = OrderDelivery.newOrderDelivery(
                    orderSaved.getId(),
                    createOrderUseCaseInput.deliveryComplementInput().address().addressLine(),
                    createOrderUseCaseInput.deliveryComplementInput().address().addressNumber(),
                    createOrderUseCaseInput.deliveryComplementInput().address().zipCode(),
                    createOrderUseCaseInput.deliveryComplementInput().address().neighborhood(),
                    createOrderUseCaseInput.deliveryComplementInput().address().city(),
                    createOrderUseCaseInput.deliveryComplementInput().address().uf(),
                    createOrderUseCaseInput.deliveryComplementInput().address().complement()
            );

            this.saveOrderDelivery(newOrderDelivery);

            final CreateFreightUseCaseInput createFreightUseCaseInput = CreateFreightUseCaseInput.with(
                    orderSaved.getId(),
                    createOrderUseCaseInput.deliveryComplementInput().freight().name(),
                    createOrderUseCaseInput.deliveryComplementInput().freight().companyName(),
                    createOrderUseCaseInput.deliveryComplementInput().freight().price(),
                    createOrderUseCaseInput.deliveryComplementInput().freight().estimatedTime(),
                    createOrderUseCaseInput.deliveryComplementInput().freight().minTime(),
                    createOrderUseCaseInput.deliveryComplementInput().freight().maxTime()
            );

            this.createFreightUseCase.execute(createFreightUseCaseInput);
        });
    }

    private String getAuthenticatedUserId() {
        return this.securityService.getUserId();
    }

    private Order saveOrder(Order order) {
        return this.orderRepository.save(order);
    }

    private void saveOrderItems(List<OrderItem> orderItems) {
        this.orderItemRepository.saveAll(orderItems);
    }

    private void saveOrderDelivery(OrderDelivery orderDelivery) {
        this.orderDeliveryRepository.save(orderDelivery);
    }
}
