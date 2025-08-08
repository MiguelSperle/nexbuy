package com.miguelsperle.nexbuy.module.coupon.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.*;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouponUseCasesConfiguration {
    @Bean
    public CreateCouponUseCase createCouponUseCase(CouponRepository couponRepository) {
        return new CreateCouponUseCaseImpl(couponRepository);
    }

    @Bean
    public UpdateCouponUseCase updateCouponUseCase(CouponRepository couponRepository) {
        return new UpdateCouponUseCaseImpl(couponRepository);
    }

    @Bean
    public ActivateCouponUseCase activateCouponUseCase(CouponRepository couponRepository) {
        return new ActivateCouponUseCaseImpl(couponRepository);
    }

    @Bean
    public DeactivateCouponUseCase deactivateCouponUseCase(CouponRepository couponRepository) {
        return new DeactivateCouponUseCaseImpl(couponRepository);
    }

    @Bean
    public DeleteCouponUseCase deleteCouponUseCase(CouponRepository couponRepository) {
        return new DeleteCouponUseCaseImpl(couponRepository);
    }
}
