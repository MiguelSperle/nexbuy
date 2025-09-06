package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.out.persistence.jpa.entities.JpaPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaPaymentRepository extends JpaRepository<JpaPaymentEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM payments p WHERE p.status = :status")
    List<JpaPaymentEntity> findAllByStatus(@Param("status") String status);
}
