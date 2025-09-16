package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.out.persistence.jpa.entities.JpaFreightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaFreightRepository extends JpaRepository<JpaFreightEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM freights f WHERE f.order_id = :orderId")
    Optional<JpaFreightEntity> findByOrderId(@Param("orderId") String orderId);
}
