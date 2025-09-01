package com.miguelsperle.nexbuy.module.payments.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.payments.infrastructure.adapters.out.persistence.jpa.entities.JpaPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends JpaRepository<JpaPaymentEntity, String> {
}
