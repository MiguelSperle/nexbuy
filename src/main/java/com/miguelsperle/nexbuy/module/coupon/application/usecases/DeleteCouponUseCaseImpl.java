package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.usecases.DeleteCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.DeleteCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class DeleteCouponUseCaseImpl implements DeleteCouponUseCase {
    private final CouponRepository couponRepository;

    public DeleteCouponUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void execute(DeleteCouponUseCaseInput deleteCouponUseCaseInput) {
        final Coupon coupon = this.getCouponById(deleteCouponUseCaseInput.couponId());

        this.deleteCouponById(coupon.getId());
    }

    private Coupon getCouponById(String couponId) {
        return this.couponRepository.findById(couponId)
                .orElseThrow(() -> NotFoundException.with("Coupon not found"));
    }

    private void deleteCouponById(String couponId) {
        this.couponRepository.deleteById(couponId);
    }
}
