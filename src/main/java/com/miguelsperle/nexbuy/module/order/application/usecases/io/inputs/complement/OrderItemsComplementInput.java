package com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.complement;

import java.math.BigDecimal;

public record OrderItemsComplementInput(
        String productId,
        Integer quantity,
        BigDecimal unitPrice
) {
    public static OrderItemsComplementInput with(
            String productId,
            Integer quantity,
            BigDecimal unitPrice
    ) {
        return new OrderItemsComplementInput(
                productId,
                quantity,
                unitPrice
        );
    }
}
