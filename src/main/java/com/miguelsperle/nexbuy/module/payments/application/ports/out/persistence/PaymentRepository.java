package com.miguelsperle.nexbuy.module.payments.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.payments.domain.entities.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    List<Payment> findAll();
    Optional<Payment> findById(String id);
    Payment save(Payment payment);
    void deleteById(String id);
}
