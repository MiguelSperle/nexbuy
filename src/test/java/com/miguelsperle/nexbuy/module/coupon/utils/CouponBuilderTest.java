package com.miguelsperle.nexbuy.module.coupon.utils;

import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class CouponBuilderTest {
    public static Coupon create() {
        return Coupon.with(
                IdentifierUtils.generateUUID(),
                "test-code",
                5,
                DecimalUtils.valueOf(100),
                false,
                TimeUtils.now().plusMinutes(15),
                TimeUtils.now()
        );
    }
}
