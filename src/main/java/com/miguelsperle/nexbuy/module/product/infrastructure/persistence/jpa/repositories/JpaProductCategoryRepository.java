package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductCategoryRepository extends JpaRepository<JpaProductCategoryEntity, String> {
}
