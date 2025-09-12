package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.out.services;

import com.miguelsperle.nexbuy.module.payment.application.ports.out.services.PaymentService;
import com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.out.services.exceptions.FailedToCreateCheckoutSessionException;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class StripePaymentServiceImpl implements PaymentService {
    @Value("${spring.stripe.api.secret.key}")
    private String stripeApiSecretKey;

    private static final Logger log = LoggerFactory.getLogger(StripePaymentServiceImpl.class);

    @PostConstruct
    public void init() {
        Stripe.apiKey = this.stripeApiSecretKey;
    }

    @Override
    public String createCheckoutSession(String paymentId, long totalAmountInCents) {
        try {
            final SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
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

            return session.getUrl();
        } catch (Exception ex) {
            log.error("Failed to create checkout session", ex);
            throw FailedToCreateCheckoutSessionException.with("Failed to create checkout session", ex);
        }
    }
}
