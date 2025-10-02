package com.miguelsperle.nexbuy.module.order.utils;

import com.miguelsperle.nexbuy.module.order.domain.entities.OrderDelivery;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class OrderDeliveryBuilderTest {
    public static OrderDelivery create(String orderId) {
        return OrderDelivery.with(
                IdentifierUtils.generateUUID(),
                orderId,
                "Wayne Avenue",
                "150A",
                "21000-200",
                "Wayne Island",
                "Gotham",
                "SP",
                "Near of Main Avenue",
                TimeUtils.now()
        );
    }
}
