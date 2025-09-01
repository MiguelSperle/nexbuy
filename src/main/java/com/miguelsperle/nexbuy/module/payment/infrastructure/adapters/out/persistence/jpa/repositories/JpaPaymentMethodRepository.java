package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.out.persistence.jpa.entities.JpaPaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaPaymentMethodRepository extends JpaRepository<JpaPaymentMethodEntity, String> {
    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM payment_methods pm WHERE LOWER(pm.name) = LOWER(:name))")
    boolean existsByName(@Param("name") String name);
}
