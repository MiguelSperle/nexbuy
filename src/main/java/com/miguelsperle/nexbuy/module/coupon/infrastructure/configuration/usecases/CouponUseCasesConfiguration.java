package com.miguelsperle.nexbuy.module.coupon.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.ActivateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.in.CreateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.in.DeactivateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.in.UpdateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.ActivateCouponUseCaseImpl;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.CreateCouponUseCaseImpl;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.DeactivateCouponUseCaseImpl;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.UpdateCouponUseCaseImpl;
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
}
