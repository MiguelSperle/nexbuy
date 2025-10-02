package com.miguelsperle.nexbuy.module.order.application.usecases;

import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderDeliveryRepository;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderItemRepository;
import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderRepository;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.GetOrderUseCaseInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs.GetOrderUseCaseOutput;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderDelivery;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderItem;
import com.miguelsperle.nexbuy.module.order.utils.OrderBuilderTest;
import com.miguelsperle.nexbuy.module.order.utils.OrderDeliveryBuilderTest;
import com.miguelsperle.nexbuy.module.order.utils.OrderItemBuilderTest;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetOrderUseCaseTest {
    @InjectMocks
    private GetOrderUseCaseImpl getOrderUseCase;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderDeliveryRepository orderDeliveryRepository;

    @Test
    @DisplayName("Should get order")
    public void should_get_order() {
        final Order order = OrderBuilderTest.create();
        final OrderItem orderItem = OrderItemBuilderTest.create(order.getId());
        final OrderDelivery orderDelivery = OrderDeliveryBuilderTest.create(order.getId());

        final List<OrderItem> orderItems = List.of(orderItem);

        Mockito.when(this.orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));

        Mockito.when(this.orderItemRepository.findAllByOrderId(Mockito.any())).thenReturn(orderItems);

        Mockito.when(this.orderDeliveryRepository.findByOrderId(Mockito.any())).thenReturn(Optional.of(orderDelivery));

        final GetOrderUseCaseInput getOrderUseCaseInput = GetOrderUseCaseInput.with(order.getId());

        final GetOrderUseCaseOutput getOrderUseCaseOutput = this.getOrderUseCase.execute(getOrderUseCaseInput);

        Assertions.assertNotNull(getOrderUseCaseOutput);
        Assertions.assertNotNull(getOrderUseCaseOutput.order());
        Assertions.assertNotNull(getOrderUseCaseOutput.orderItems());
        Assertions.assertNotNull(getOrderUseCaseOutput.orderDelivery());

        Assertions.assertEquals(order, getOrderUseCaseOutput.order());
        Assertions.assertEquals(orderItems, getOrderUseCaseOutput.orderItems());
        Assertions.assertEquals(orderDelivery, getOrderUseCaseOutput.orderDelivery());

        Mockito.verify(this.orderRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.orderItemRepository, Mockito.times(1)).findAllByOrderId(Mockito.any());
        Mockito.verify(this.orderDeliveryRepository, Mockito.times(1)).findByOrderId(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when order does not exist")
    public void should_throw_NotFoundException_when_order_does_not_exist() {
        final Order order = OrderBuilderTest.create();

        Mockito.when(this.orderRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final GetOrderUseCaseInput getOrderUseCaseInput = GetOrderUseCaseInput.with(order.getId());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getOrderUseCase.execute(getOrderUseCaseInput)
        );

        final String expectedErrorMessage = "Order not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.orderRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when order delivery does not exist")
    public void should_throw_NotFoundException_when_order_delivery_does_not_exist() {
        final Order order = OrderBuilderTest.create();

        Mockito.when(this.orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));

        Mockito.when(this.orderDeliveryRepository.findByOrderId(Mockito.any())).thenReturn(Optional.empty());

        final GetOrderUseCaseInput getOrderUseCaseInput = GetOrderUseCaseInput.with(order.getId());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getOrderUseCase.execute(getOrderUseCaseInput)
        );

        final String expectedErrorMessage = "Order delivery not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.orderRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.orderDeliveryRepository, Mockito.times(1)).findByOrderId(Mockito.any());
    }
}
