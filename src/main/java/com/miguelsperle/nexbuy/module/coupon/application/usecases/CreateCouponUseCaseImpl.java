package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.CreateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.CreateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.domain.enums.CouponType;
import com.miguelsperle.nexbuy.module.coupon.domain.exceptions.CouponAlreadyExistsException;
import com.miguelsperle.nexbuy.module.coupon.domain.exceptions.InvalidCouponConfigurationException;

public class CreateCouponUseCaseImpl implements CreateCouponUseCase {
    private final CouponRepository couponRepository;

    public CreateCouponUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void execute(CreateCouponUseCaseInput createCouponUseCaseInput) {
        if (this.verifyCouponAlreadyExistsByCode(createCouponUseCaseInput.code())) {
            throw CouponAlreadyExistsException.with("Coupon with this code already exists");
        }

        final CouponType convertedToCouponType = CouponType.valueOf(createCouponUseCaseInput.couponType());

        if (convertedToCouponType == CouponType.UNLIMITED && createCouponUseCaseInput.usageLimit() != null) {
            throw InvalidCouponConfigurationException.with("Unlimited coupon should not have a usage limit");
        }

        if (convertedToCouponType == CouponType.LIMITED && createCouponUseCaseInput.usageLimit() == null) {
            throw InvalidCouponConfigurationException.with("Limited coupon should have a usage limit");
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
