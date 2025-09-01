package com.miguelsperle.nexbuy.module.payments.infrastructure.adapters.out.persistence;

import com.miguelsperle.nexbuy.module.payments.application.ports.out.persistence.PaymentMethodRepository;
import com.miguelsperle.nexbuy.module.payments.domain.entities.PaymentMethod;
import com.miguelsperle.nexbuy.module.payments.infrastructure.adapters.out.persistence.jpa.entities.JpaPaymentMethodEntity;
import com.miguelsperle.nexbuy.module.payments.infrastructure.adapters.out.persistence.jpa.repositories.JpaPaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentMethodRepositoryImpl implements PaymentMethodRepository {
    private final JpaPaymentMethodRepository jpaPaymentMethodRepository;

    @Override
    public List<PaymentMethod> findAll() {
        return this.jpaPaymentMethodRepository.findAll().stream().map(JpaPaymentMethodEntity::toEntity).toList();
    }

    @Override
    public Optional<PaymentMethod> findById(String id) {
        return this.jpaPaymentMethodRepository.findById(id).map(JpaPaymentMethodEntity::toEntity);
    }

    @Override
    public PaymentMethod save(PaymentMethod paymentMethod) {
        return this.jpaPaymentMethodRepository.save(JpaPaymentMethodEntity.from(paymentMethod)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaPaymentMethodRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return this.jpaPaymentMethodRepository.existsByName(name);
    }
}
