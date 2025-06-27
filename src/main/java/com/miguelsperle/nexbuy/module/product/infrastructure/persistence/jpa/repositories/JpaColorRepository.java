package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaColorRepository extends JpaRepository<JpaColorEntity, String> {
}
