package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities.JpaOrderDeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaOrderDeliveryRepository extends JpaRepository<JpaOrderDeliveryEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM order_deliveries od WHERE od.order_id = :orderId")
    Optional<JpaOrderDeliveryEntity> findByOrderId(@Param("orderId") String orderId);
}
