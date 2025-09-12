package com.miguelsperle.nexbuy.module.payment.application.usecases;

import com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases.CreatePaymentUseCase;
import com.miguelsperle.nexbuy.module.payment.application.ports.out.persistence.PaymentRepository;
import com.miguelsperle.nexbuy.module.payment.application.ports.out.services.PaymentService;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.CreatePaymentUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.CreatePaymentUseCaseOutput;
import com.miguelsperle.nexbuy.module.payment.domain.entities.Payment;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;

import java.util.concurrent.atomic.AtomicReference;

public class CreatePaymentUseCaseImpl implements CreatePaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final TransactionExecutor transactionExecutor;
    private final SecurityContextService securityContextService;

    public CreatePaymentUseCaseImpl(
            PaymentRepository paymentRepository,
            PaymentService paymentService,
            TransactionExecutor transactionExecutor,
            SecurityContextService securityContextService
    ) {
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
        this.transactionExecutor = transactionExecutor;
        this.securityContextService = securityContextService;
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

        this.transactionExecutor.runTransaction(() -> {
            final Payment savedPayment = this.savePayment(newPayment);

            final long convertedToCents = createPaymentUseCaseInput.totalAmount().multiply(DecimalUtils.valueOf(100)).longValue();

            final String sessionUrl = this.paymentService.createCheckoutSession(savedPayment.getId(), convertedToCents);

            sessionUrlRef.set(sessionUrl);
        });

        return CreatePaymentUseCaseOutput.from(sessionUrlRef.get());
    }

    private String getAuthenticatedUserId() {
        return this.securityContextService.getAuthenticatedUserId();
    }

    private Payment savePayment(Payment payment) {
        return this.paymentRepository.save(payment);
    }
}
