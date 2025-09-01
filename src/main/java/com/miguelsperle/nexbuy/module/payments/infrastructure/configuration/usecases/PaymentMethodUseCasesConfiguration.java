package com.miguelsperle.nexbuy.module.payments.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.payments.application.ports.in.usecases.CreatePaymentMethodUseCase;
import com.miguelsperle.nexbuy.module.payments.application.ports.in.usecases.UpdatePaymentMethodUseCase;
import com.miguelsperle.nexbuy.module.payments.application.ports.out.persistence.PaymentMethodRepository;
import com.miguelsperle.nexbuy.module.payments.application.usecases.CreatePaymentMethodUseCaseImpl;
import com.miguelsperle.nexbuy.module.payments.application.usecases.UpdatePaymentMethodUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentMethodUseCasesConfiguration {
    @Bean
    public CreatePaymentMethodUseCase createPaymentMethodUseCase(PaymentMethodRepository paymentMethodRepository) {
        return new CreatePaymentMethodUseCaseImpl(paymentMethodRepository);
    }

    @Bean
    public UpdatePaymentMethodUseCase updatePaymentMethodUseCase(PaymentMethodRepository paymentMethodRepository) {
        return new UpdatePaymentMethodUseCaseImpl(paymentMethodRepository);
    }
}
