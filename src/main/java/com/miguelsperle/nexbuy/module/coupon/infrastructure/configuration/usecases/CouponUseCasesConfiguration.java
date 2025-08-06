package com.miguelsperle.nexbuy.module.coupon.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.CreateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.CreateCouponUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouponUseCasesConfiguration {
    @Bean
    public CreateCouponUseCase createCouponUseCase(CouponRepository couponRepository) {
        return new CreateCouponUseCaseImpl(couponRepository);
    }
}
