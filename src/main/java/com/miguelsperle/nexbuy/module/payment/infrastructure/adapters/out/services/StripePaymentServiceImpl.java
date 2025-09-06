package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.out.services;

import com.miguelsperle.nexbuy.module.payment.application.ports.out.services.PaymentService;
import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentMethod;
import com.miguelsperle.nexbuy.module.payment.domain.payment.PaymentGatewayInfo;
import com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.out.services.exceptions.PaymentFailedException;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class StripePaymentServiceImpl implements PaymentService {
    @Value("${spring.stripe.api.secret.key}")
    private String stripeApiSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = this.stripeApiSecretKey;
    }

    @Override
    public PaymentGatewayInfo createCheckoutSession(String paymentId, long totalAmountInCents, PaymentMethod paymentMethod) {
        try {
            final SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .addPaymentMethodType(
                            paymentMethod == PaymentMethod.CARD
                                    ? SessionCreateParams.PaymentMethodType.CARD
                                    : SessionCreateParams.PaymentMethodType.BOLETO
                    )
                    .setSuccessUrl("https://yourwebsite.com/payment/success")
                    .setCancelUrl("https://yourwebsite.com/payment/cancel")
                    .setExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES).getEpochSecond())
                    .putMetadata("paymentId", paymentId)
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("BRL")
                                    .setUnitAmount(totalAmountInCents)
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName("Total order value + freight")
                                            .build()
                                    ).build()
                            ).build()
                    ).build();

            final Session session = Session.create(sessionCreateParams);

            return new PaymentGatewayInfo(
                    session.getId(),
                    session.getUrl()
            );
        } catch (Exception exception) {
            throw PaymentFailedException.with("Payment failed", exception);
        }
    }
}
