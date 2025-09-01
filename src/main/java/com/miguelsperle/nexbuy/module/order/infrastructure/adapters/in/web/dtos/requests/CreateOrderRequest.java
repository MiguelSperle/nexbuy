package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.dtos.requests;

import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.dtos.requests.complement.DeliveryComplementRequest;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.in.web.dtos.requests.complement.OrderItemsComplementRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
        @NotBlank(message = "Payment method id should not neither be null nor blank")
        String paymentMethodId,

        @NotNull(message = "Total amount should not be null")
        @Positive(message = "Total amount should be a positive number")
        @Digits(integer = 17, fraction = 2, message = "Total amount should have up to 17 digits before the decimal point and 2 after")
        BigDecimal totalAmount,

        @NotNull(message = "Order items should not be null")
        @Valid
        List<OrderItemsComplementRequest> orderItems,

        @NotNull(message = "Delivery should not be null")
        @Valid
        DeliveryComplementRequest delivery
) {
}
