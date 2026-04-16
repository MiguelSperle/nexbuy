package com.miguelsperle.nexbuy.module.order.infrastructure.rest.dtos.res.complements;

import java.math.BigDecimal;

public record OrderItemsComplementResponse(
        String id,
        String productId,
        Integer quantity,
        BigDecimal unitPrice
) {
    public static OrderItemsComplementResponse from(
            String id,
            String productId,
            Integer quantity,
            BigDecimal unitPrice
    ) {
        return new OrderItemsComplementResponse(
                id,
                productId,
                quantity,
                unitPrice
        );
    }
}
