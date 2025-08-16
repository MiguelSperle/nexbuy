package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.usecases.CreateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.CreateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.domain.enums.CouponType;
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

        final CouponType convertedToCouponType = CouponType.valueOf(createCouponUseCaseInput.couponType());

        if (convertedToCouponType == CouponType.UNLIMITED && createCouponUseCaseInput.usageLimit() != null) {
            throw DomainException.with("Unlimited coupon should not have a usage limit", 400);
        }

        if (convertedToCouponType == CouponType.LIMITED && createCouponUseCaseInput.usageLimit() == null) {
            throw DomainException.with("Limited coupon should have a usage limit", 400);
        }

        final Coupon newCoupon = Coupon.newCoupon(
                createCouponUseCaseInput.code(),
                createCouponUseCaseInput.percentage(),
                createCouponUseCaseInput.minimumPurchaseAmount(),
                convertedToCouponType,
                createCouponUseCaseInput.usageLimit(),
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
