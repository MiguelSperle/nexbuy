package com.miguelsperle.nexbuy.module.payment.application.usecases;

import com.miguelsperle.nexbuy.module.payment.application.abstractions.usecases.CreatePaymentUseCase;
import com.miguelsperle.nexbuy.module.payment.application.abstractions.repositories.PaymentRepository;
import com.miguelsperle.nexbuy.module.payment.application.abstractions.services.PaymentService;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.CreatePaymentUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.CreatePaymentUseCaseOutput;
import com.miguelsperle.nexbuy.module.payment.domain.entities.Payment;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityService;
import com.miguelsperle.nexbuy.shared.application.abstractions.wrapper.TransactionManager;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;

import java.util.concurrent.atomic.AtomicReference;

public class CreatePaymentUseCaseImpl implements CreatePaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final TransactionManager transactionManager;
    private final SecurityService securityService;

    public CreatePaymentUseCaseImpl(
            PaymentRepository paymentRepository,
            PaymentService paymentService,
            TransactionManager transactionManager,
            SecurityService securityService
    ) {
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
        this.transactionManager = transactionManager;
        this.securityService = securityService;
    }

    @Override
    public CreatePaymentUseCaseOutput execute(CreatePaymentUseCaseInput createPaymentUseCaseInput) {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final Payment newPayment = Payment.newPayment(
                authenticatedUserId,
                createPaymentUseCaseInput.totalAmount(),
                createPaymentUseCaseInput.orderId()
        );

        final AtomicReference<String> sessionUrlRef = new AtomicReference<>();

        this.transactionManager.runTransaction(() -> {
            final Payment savedPayment = this.savePayment(newPayment);

            final long convertedToCents = createPaymentUseCaseInput.totalAmount().multiply(DecimalUtils.valueOf(100)).longValue();

            final String sessionUrl = this.paymentService.createCheckoutSession(savedPayment.getId(), convertedToCents);

            sessionUrlRef.set(sessionUrl);
        });

        return CreatePaymentUseCaseOutput.from(sessionUrlRef.get());
    }

    private String getAuthenticatedUserId() {
        return this.securityService.getUserId();
    }

    private Payment savePayment(Payment payment) {
        return this.paymentRepository.save(payment);
    }
}
