package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.usecases.ActivateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.ActivateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class ActivateCouponUseCaseImpl implements ActivateCouponUseCase {
    private final CouponRepository couponRepository;

    public ActivateCouponUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void execute(ActivateCouponUseCaseInput activateCouponUseCaseInput) {
        final Coupon coupon = this.getCouponById(activateCouponUseCaseInput.couponId());

        if (coupon.getIsActive()) {
            throw DomainException.with("Coupon is already activated", 409);
        }

        final Coupon updatedCoupon = coupon.withIsActive(true);

        this.saveCoupon(updatedCoupon);
    }

    private Coupon getCouponById(String couponId) {
        return this.couponRepository.findById(couponId)
                .orElseThrow(() -> NotFoundException.with("Coupon not found"));
    }

    private void saveCoupon(Coupon coupon) {
        this.couponRepository.save(coupon);
    }
}
