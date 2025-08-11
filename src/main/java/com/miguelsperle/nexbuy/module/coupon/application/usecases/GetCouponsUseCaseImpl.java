package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.GetCouponsUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.GetCouponsUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs.GetCouponsUseCaseOutput;

public class GetCouponsUseCaseImpl implements GetCouponsUseCase {
    private final CouponRepository couponRepository;

    public GetCouponsUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public GetCouponsUseCaseOutput execute(GetCouponsUseCaseInput getCouponsUseCaseInput) {
        return null;
    }
}
