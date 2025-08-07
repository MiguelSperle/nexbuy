package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.UpdateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.UpdateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.domain.exceptions.CouponAlreadyExistsException;
import com.miguelsperle.nexbuy.module.coupon.domain.exceptions.CouponNotFoundException;

public class UpdateCouponUseCaseImpl implements UpdateCouponUseCase {
    private final CouponRepository couponRepository;

    public UpdateCouponUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void execute(UpdateCouponUseCaseInput updateCouponUseCaseInput) {
        final Coupon coupon = this.getCouponById(updateCouponUseCaseInput.couponId());

        if (!updateCouponUseCaseInput.code().equalsIgnoreCase(coupon.getCode())) {
            if (this.verifyCouponAlreadyExistsByCode(updateCouponUseCaseInput.code())) {
                throw CouponAlreadyExistsException.with("Coupon with this code already exists");
            }
        }

        final Coupon updatedCoupon = coupon.withCode(updateCouponUseCaseInput.code())
                .withPercentage(updateCouponUseCaseInput.percentage())
                .withMinimumPurchaseAmount(updateCouponUseCaseInput.minimumPurchaseAmount())
                .withExpiresIn(updateCouponUseCaseInput.expiresIn());

        this.saveCoupon(updatedCoupon);
    }

    private Coupon getCouponById(String couponId) {
        return this.couponRepository.findById(couponId)
                .orElseThrow(() -> CouponNotFoundException.with("Coupon not found"));
    }

    private boolean verifyCouponAlreadyExistsByCode(String code) {
        return this.couponRepository.existsByCode(code);
    }

    private void saveCoupon(Coupon coupon) {
        this.couponRepository.save(coupon);
    }
}
