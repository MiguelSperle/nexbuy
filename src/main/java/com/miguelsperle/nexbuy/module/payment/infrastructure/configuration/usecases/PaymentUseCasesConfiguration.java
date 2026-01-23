package com.miguelsperle.nexbuy.module.payment.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases.CreatePaymentUseCase;
import com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases.GetPaymentUseCase;
import com.miguelsperle.nexbuy.module.payment.application.ports.out.persistence.PaymentRepository;
import com.miguelsperle.nexbuy.module.payment.application.ports.out.services.PaymentService;
import com.miguelsperle.nexbuy.module.payment.application.usecases.CreatePaymentUseCaseImpl;
import com.miguelsperle.nexbuy.module.payment.application.usecases.GetPaymentUseCaseImpl;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentUseCasesConfiguration {
    @Bean
    public CreatePaymentUseCase createPaymentUseCase(
            PaymentRepository paymentRepository,
            PaymentService paymentService,
            TransactionExecutor transactionExecutor,
            SecurityContextService securityContextService
    ) {
        return new CreatePaymentUseCaseImpl(
                paymentRepository,
                paymentService,
                transactionExecutor,
                securityContextService
        );
    }

    @Bean
    public GetPaymentUseCase getPaymentUseCase(PaymentRepository paymentRepository) {
        return new GetPaymentUseCaseImpl(paymentRepository);
    }
}
