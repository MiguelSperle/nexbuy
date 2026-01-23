package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.usecases.CreateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.CreateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;

public class CreateCouponUseCaseImpl implements CreateCouponUseCase {
    private final CouponRepository couponRepository;

    public CreateCouponUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void execute(CreateCouponUseCaseInput createCouponUseCaseInput) {
        if (this.verifyCouponAlreadyExistsByCode(createCouponUseCaseInput.code())) {
            throw DomainException.with("Coupon with this code already exists", 409);
        }

        final Coupon newCoupon = Coupon.newCoupon(
                createCouponUseCaseInput.code(),
                createCouponUseCaseInput.percentage(),
                createCouponUseCaseInput.minimumPurchaseAmount(),
                createCouponUseCaseInput.isActive(),
                createCouponUseCaseInput.expiresIn()
        );

        this.saveCoupon(newCoupon);
    }

    private boolean verifyCouponAlreadyExistsByCode(String code) {
        return this.couponRepository.existsByCode(code);
    }

    private void saveCoupon(Coupon coupon) {
        this.couponRepository.save(coupon);
    }
}
