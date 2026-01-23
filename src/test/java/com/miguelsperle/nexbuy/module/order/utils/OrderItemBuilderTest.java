package com.miguelsperle.nexbuy.module.order.utils;

import com.miguelsperle.nexbuy.module.order.domain.entities.OrderItem;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class OrderItemBuilderTest {
    public static OrderItem create(String orderId) {
        return OrderItem.with(
                IdentifierUtils.generateUUID(),
                orderId,
                IdentifierUtils.generateUUID(),
                1,
                DecimalUtils.valueOf(20),
                TimeUtils.now()
        );
    }
}
