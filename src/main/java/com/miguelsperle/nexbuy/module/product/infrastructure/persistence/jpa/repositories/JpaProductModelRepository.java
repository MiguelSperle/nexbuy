package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaProductModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductModelRepository extends JpaRepository<JpaProductModelEntity, String> {
}
