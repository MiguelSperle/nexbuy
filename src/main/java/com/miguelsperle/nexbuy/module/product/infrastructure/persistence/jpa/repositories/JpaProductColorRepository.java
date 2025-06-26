package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaProductColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductColorRepository extends JpaRepository<JpaProductColorEntity, String> {
}
