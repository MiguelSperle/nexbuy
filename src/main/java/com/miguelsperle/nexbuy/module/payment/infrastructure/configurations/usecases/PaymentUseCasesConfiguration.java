package com.miguelsperle.nexbuy.module.payment.infrastructure.configurations.usecases;

import com.miguelsperle.nexbuy.module.payment.application.abstractions.usecases.CreatePaymentUseCase;
import com.miguelsperle.nexbuy.module.payment.application.abstractions.usecases.GetPaymentUseCase;
import com.miguelsperle.nexbuy.module.payment.application.abstractions.repositories.PaymentRepository;
import com.miguelsperle.nexbuy.module.payment.application.abstractions.services.PaymentService;
import com.miguelsperle.nexbuy.module.payment.application.usecases.CreatePaymentUseCaseImpl;
import com.miguelsperle.nexbuy.module.payment.application.usecases.GetPaymentUseCaseImpl;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityService;
import com.miguelsperle.nexbuy.shared.application.abstractions.wrapper.TransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentUseCasesConfiguration {
    @Bean
    public CreatePaymentUseCase createPaymentUseCase(
            PaymentRepository paymentRepository,
            PaymentService paymentService,
            TransactionManager transactionManager,
            SecurityService securityService
    ) {
        return new CreatePaymentUseCaseImpl(
                paymentRepository,
                paymentService,
                transactionManager,
                securityService
        );
    }

    @Bean
    public GetPaymentUseCase getPaymentUseCase(PaymentRepository paymentRepository) {
        return new GetPaymentUseCaseImpl(paymentRepository);
    }
}
