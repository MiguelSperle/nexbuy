package com.miguelsperle.nexbuy.module.payment.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.payment.domain.entities.PaymentMethod;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodRepository {
    List<PaymentMethod> findAll();
    Optional<PaymentMethod> findById(String id);
    PaymentMethod save(PaymentMethod paymentMethod);
    void deleteById(String id);
    boolean existsByName(String name);
}
