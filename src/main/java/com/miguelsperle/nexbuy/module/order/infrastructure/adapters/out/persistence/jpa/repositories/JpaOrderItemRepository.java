package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities.JpaOrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaOrderItemRepository extends JpaRepository<JpaOrderItemEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM order_items oi WHERE oi.order_id = :orderId")
    List<JpaOrderItemEntity> findAllByOrderId(@Param("orderId") String orderId);
}
