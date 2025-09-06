package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.order.application.ports.in.usecases.CreateOrderUseCase;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.CreateOrderUseCaseInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.complement.*;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.dtos.requests.CreateOrderRequest;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        final List<OrderItemsComplementInput> orderItemsComplementInput = createOrderRequest.orderItems().stream()
                .map(orderItem -> OrderItemsComplementInput.with(
                        orderItem.productId(),
                        orderItem.quantity(),
                        orderItem.unitPrice()
                )).toList();

        final AddressComplementInput addressComplementInput = AddressComplementInput.with(
                createOrderRequest.delivery().address().addressLine(),
                createOrderRequest.delivery().address().addressNumber(),
                createOrderRequest.delivery().address().zipCode(),
                createOrderRequest.delivery().address().neighborhood(),
                createOrderRequest.delivery().address().city(),
                createOrderRequest.delivery().address().uf(),
                createOrderRequest.delivery().address().complement()
        );

        final FreightComplementInput freightComplementInput = FreightComplementInput.with(
                createOrderRequest.delivery().freight().name(),
                createOrderRequest.delivery().freight().companyName(),
                createOrderRequest.delivery().freight().price(),
                createOrderRequest.delivery().freight().estimatedTime(),
                createOrderRequest.delivery().freight().minTime(),
                createOrderRequest.delivery().freight().maxTime()
        );

        final DeliveryComplementInput deliveryComplementInput = DeliveryComplementInput.with(
                addressComplementInput, freightComplementInput
        );

        this.createOrderUseCase.execute(CreateOrderUseCaseInput.with(
                createOrderRequest.totalAmount(),
                orderItemsComplementInput,
                deliveryComplementInput
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Order created successfully"));
    }
}
