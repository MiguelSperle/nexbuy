package com.miguelsperle.nexbuy.module.order.application.usecases;

import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderDeliveryRepository;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderItemRepository;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderRepository;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.CreateOrderUseCaseInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.complement.AddressComplementInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.complement.DeliveryComplementInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.complement.FreightComplementInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.complement.OrderItemsComplementInput;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderDelivery;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderItem;
import com.miguelsperle.nexbuy.module.order.utils.OrderBuilderTest;
import com.miguelsperle.nexbuy.module.order.utils.OrderDeliveryBuilderTest;
import com.miguelsperle.nexbuy.module.order.utils.OrderItemBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.CodeProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CreateOrderUseCaseTest {
    @InjectMocks
    private CreateOrderUseCaseImpl createOrderUseCase;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderDeliveryRepository orderDeliveryRepository;

    @Mock
    private SecurityContextService securityContextService;

    @Mock
    private CodeProvider codeProvider;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Mock
    private MessageProducer messageProducer;

    @Test
    @DisplayName("Should create order")
    public void should_create_order() {
        final Order order = OrderBuilderTest.create();
        final OrderItem orderItem = OrderItemBuilderTest.create(order.getId());
        final OrderDelivery orderDelivery = OrderDeliveryBuilderTest.create(order.getId());

        Mockito.when(this.securityContextService.getAuthenticatedUserId()).thenReturn(order.getUserId());

        Mockito.when(this.codeProvider.generateCode(Mockito.anyInt(), Mockito.any())).thenReturn(order.getOrderNumber());

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        Mockito.when(this.orderRepository.save(Mockito.any())).thenReturn(order);

        Mockito.when(this.orderItemRepository.saveAll(Mockito.any())).thenReturn(List.of(orderItem));

        Mockito.when(this.orderDeliveryRepository.save(Mockito.any())).thenReturn(orderDelivery);

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).registerAfterCommit(Mockito.any());

        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        final OrderItemsComplementInput orderItemsComplementInput = OrderItemsComplementInput.with(
                orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice()
        );

        final AddressComplementInput addressComplementInput = AddressComplementInput.with(
                orderDelivery.getAddressLine(),
                orderDelivery.getAddressNumber(),
                orderDelivery.getZipCode(),
                orderDelivery.getNeighborhood(),
                orderDelivery.getCity(),
                orderDelivery.getUf(),
                orderDelivery.getComplement()
        );

        final FreightComplementInput freightComplementInput = FreightComplementInput.with(
                "Sedex",
                "Correios",
                DecimalUtils.valueOf(25),
                5,
                2,
                5
        );

        final DeliveryComplementInput deliveryComplementInput = DeliveryComplementInput.with(
                addressComplementInput,
                freightComplementInput
        );

        final CreateOrderUseCaseInput createOrderUseCaseInput = CreateOrderUseCaseInput.with(
                order.getTotalAmount(),
                List.of(orderItemsComplementInput),
                deliveryComplementInput
        );

        this.createOrderUseCase.execute(createOrderUseCaseInput);

        Mockito.verify(this.securityContextService, Mockito.times(1)).getAuthenticatedUserId();
        Mockito.verify(this.codeProvider, Mockito.times(1)).generateCode(Mockito.anyInt(), Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.orderRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.orderItemRepository, Mockito.times(1)).saveAll(Mockito.any());
        Mockito.verify(this.orderDeliveryRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).registerAfterCommit(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }
}
