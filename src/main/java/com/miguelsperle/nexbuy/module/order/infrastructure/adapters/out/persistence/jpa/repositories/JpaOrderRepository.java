package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities.JpaOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaOrderRepository extends JpaRepository<JpaOrderEntity, String>, JpaSpecificationExecutor<JpaOrderEntity> {
}
