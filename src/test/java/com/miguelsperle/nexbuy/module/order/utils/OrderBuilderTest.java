package com.miguelsperle.nexbuy.module.order.utils;

import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.domain.enums.OrderStatus;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class OrderBuilderTest {
    public static Order create() {
        return Order.with(
                IdentifierUtils.generateUUID(),
                IdentifierUtils.generateUUID(),
                OrderStatus.WAITING_PAYMENT,
                "test-order-number",
                DecimalUtils.valueOf(125),
                TimeUtils.now()
        );
    }
}
