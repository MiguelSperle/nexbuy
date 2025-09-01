package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities.JpaOrderDeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderDeliveryRepository extends JpaRepository<JpaOrderDeliveryEntity, String> {
}
