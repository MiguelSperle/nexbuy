package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaProductBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductBrandRepository extends JpaRepository<JpaProductBrandEntity, String> {
}
