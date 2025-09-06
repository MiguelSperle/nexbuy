package com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.complement.DeliveryComplementInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.complement.OrderItemsComplementInput;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderUseCaseInput(
        BigDecimal totalAmount,
        List<OrderItemsComplementInput> orderItemsComplementInput,
        DeliveryComplementInput deliveryComplementInput
) {
    public static CreateOrderUseCaseInput with(
            BigDecimal totalAmount,
            List<OrderItemsComplementInput> orderItemsComplementInput,
            DeliveryComplementInput deliveryComplementInput
    ) {
        return new CreateOrderUseCaseInput(
                totalAmount,
                orderItemsComplementInput,
                deliveryComplementInput
        );
    }
}
