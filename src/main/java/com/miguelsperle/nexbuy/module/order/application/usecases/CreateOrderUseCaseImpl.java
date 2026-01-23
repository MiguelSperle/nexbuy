package com.miguelsperle.nexbuy.module.order.application.usecases;

import com.miguelsperle.nexbuy.module.order.application.ports.in.usecases.CreateOrderUseCase;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderDeliveryRepository;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderItemRepository;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderRepository;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.CreateOrderUseCaseInput;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderDelivery;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderItem;
import com.miguelsperle.nexbuy.shared.application.commands.CreateFreightCommand;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.CodeProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;

import java.util.List;

public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderDeliveryRepository orderDeliveryRepository;
    private final SecurityContextService securityContextService;
    private final CodeProvider codeProvider;
    private final TransactionExecutor transactionExecutor;
    private final MessageProducer messageProducer;

    private final static int CODE_LENGTH = 12;
    private final static String NUMERIC_CHARACTERS = "0123456789";

    private final static String CREATE_FREIGHT_EXCHANGE = "create.freight.exchange";
    private final static String CREATE_FREIGHT_ROUTING_KEY = "create.freight.routing.key";

    public CreateOrderUseCaseImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            OrderDeliveryRepository orderDeliveryRepository,
            SecurityContextService securityContextService,
            CodeProvider codeProvider,
            TransactionExecutor transactionExecutor,
            MessageProducer messageProducer
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderDeliveryRepository = orderDeliveryRepository;
        this.securityContextService = securityContextService;
        this.codeProvider = codeProvider;
        this.transactionExecutor = transactionExecutor;
        this.messageProducer = messageProducer;
    }

    @Override
    public void execute(CreateOrderUseCaseInput createOrderUseCaseInput) {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final String codeGenerated = this.codeProvider.generateCode(CODE_LENGTH, NUMERIC_CHARACTERS);

        final Order newOrder = Order.newOrder(authenticatedUserId, codeGenerated, createOrderUseCaseInput.totalAmount());

        this.transactionExecutor.runTransaction(() -> {
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

            final CreateFreightCommand createFreightCommand = CreateFreightCommand.with(
                    orderSaved.getId(),
                    createOrderUseCaseInput.deliveryComplementInput().freight().name(),
                    createOrderUseCaseInput.deliveryComplementInput().freight().companyName(),
                    createOrderUseCaseInput.deliveryComplementInput().freight().price(),
                    createOrderUseCaseInput.deliveryComplementInput().freight().estimatedTime(),
                    createOrderUseCaseInput.deliveryComplementInput().freight().minTime(),
                    createOrderUseCaseInput.deliveryComplementInput().freight().maxTime()
            );

            this.transactionExecutor.registerAfterCommit(() ->
                    this.messageProducer.publish(CREATE_FREIGHT_EXCHANGE, CREATE_FREIGHT_ROUTING_KEY, createFreightCommand)
            );
        });
    }

    private String getAuthenticatedUserId() {
        return this.securityContextService.getAuthenticatedUserId();
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
