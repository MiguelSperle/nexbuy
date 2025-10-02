package com.miguelsperle.nexbuy.module.payment.utils;

import com.miguelsperle.nexbuy.module.payment.domain.entities.Payment;
import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentMethod;
import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentStatus;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class PaymentBuilderTest {
    public static Payment create() {
        return Payment.with(
                IdentifierUtils.generateUUID(),
                IdentifierUtils.generateUUID(),
                PaymentMethod.CARD,
                DecimalUtils.valueOf(100),
                IdentifierUtils.generateUUID(),
                PaymentStatus.PENDING,
                TimeUtils.now()
        );
    }
}
