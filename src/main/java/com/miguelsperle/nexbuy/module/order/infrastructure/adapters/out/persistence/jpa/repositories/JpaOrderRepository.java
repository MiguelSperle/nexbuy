package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities.JpaOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaOrderRepository extends JpaRepository<JpaOrderEntity, String>, JpaSpecificationExecutor<JpaOrderEntity> {
    @Query(nativeQuery = true, value = "SELECT * FROM orders o WHERE o.status = :status")
    List<JpaOrderEntity> findAllOrdersByStatus(@Param("status") String status);
}
