package com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.product.infrastructure.persistence.jpa.entities.JpaModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaModelRepository extends JpaRepository<JpaModelEntity, String> {
}
