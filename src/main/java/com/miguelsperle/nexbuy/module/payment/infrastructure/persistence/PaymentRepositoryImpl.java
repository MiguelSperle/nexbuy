package com.miguelsperle.nexbuy.module.payment.infrastructure.persistence;

import com.miguelsperle.nexbuy.module.payment.application.abstractions.repositories.PaymentRepository;
import com.miguelsperle.nexbuy.module.payment.domain.entities.Payment;
import com.miguelsperle.nexbuy.module.payment.infrastructure.persistence.jpa.entities.JpaPaymentEntity;
import com.miguelsperle.nexbuy.module.payment.infrastructure.persistence.jpa.repositories.JpaPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final JpaPaymentRepository jpaPaymentRepository;

    @Override
    public List<Payment> findAll() {
        return this.jpaPaymentRepository.findAll().stream().map(JpaPaymentEntity::toEntity).toList();
    }

    @Override
    public Optional<Payment> findById(String id) {
        return this.jpaPaymentRepository.findById(id).map(JpaPaymentEntity::toEntity);
    }

    @Override
    public Payment save(Payment payment) {
        return this.jpaPaymentRepository.save(JpaPaymentEntity.from(payment)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaPaymentRepository.deleteById(id);
    }

    @Override
    public Optional<Payment> findByOrderId(String orderId) {
        return this.jpaPaymentRepository.findByOrderId(orderId).map(JpaPaymentEntity::toEntity);
    }
}
