package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.ActivateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.ActivateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.domain.exceptions.CouponActivatedException;
import com.miguelsperle.nexbuy.module.coupon.domain.exceptions.CouponNotFoundException;

public class ActivateCouponUseCaseImpl implements ActivateCouponUseCase {
    private final CouponRepository couponRepository;

    public ActivateCouponUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void execute(ActivateCouponUseCaseInput activateCouponUseCaseInput) {
        final Coupon coupon = this.getCouponById(activateCouponUseCaseInput.couponId());

        if (coupon.getIsActive()) {
            throw CouponActivatedException.with("Coupon is already activated");
        }

        final Coupon updatedCoupon = coupon.withIsActive(true);

        this.saveCoupon(updatedCoupon);
    }

    private Coupon getCouponById(String couponId) {
        return this.couponRepository.findById(couponId)
                .orElseThrow(() -> CouponNotFoundException.with("Coupon not found"));
    }

    private void saveCoupon(Coupon coupon) {
        this.couponRepository.save(coupon);
    }
}
