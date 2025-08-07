package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.DeactivateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.DeactivateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.domain.exceptions.CouponAlreadyDeactivatedException;
import com.miguelsperle.nexbuy.module.coupon.domain.exceptions.CouponNotFoundException;

public class DeactivateCouponUseCaseImpl implements DeactivateCouponUseCase {
    private final CouponRepository couponRepository;

    public DeactivateCouponUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void execute(DeactivateCouponUseCaseInput deactivateCouponUseCaseInput) {
        final Coupon coupon = this.getCouponById(deactivateCouponUseCaseInput.couponId());

        if (!coupon.getIsActive()) {
            throw CouponAlreadyDeactivatedException.with("Coupon is already deactivated");
        }

        final Coupon updatedCoupon = coupon.withIsActive(false);

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
