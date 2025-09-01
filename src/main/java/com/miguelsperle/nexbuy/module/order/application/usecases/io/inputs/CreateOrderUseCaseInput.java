package com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs;

import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.complement.DeliveryComplementInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.complement.OrderItemsComplementInput;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderUseCaseInput(
        String paymentMethodId,
        BigDecimal totalAmount,
        List<OrderItemsComplementInput> orderItemsComplementInput,
        DeliveryComplementInput deliveryComplementInput
) {
    public static CreateOrderUseCaseInput with(
            String paymentMethodId,
            BigDecimal totalAmount,
            List<OrderItemsComplementInput> orderItemsComplementInput,
            DeliveryComplementInput deliveryComplementInput
    ) {
        return new CreateOrderUseCaseInput(
                paymentMethodId,
                totalAmount,
                orderItemsComplementInput,
                deliveryComplementInput
        );
    }
}
