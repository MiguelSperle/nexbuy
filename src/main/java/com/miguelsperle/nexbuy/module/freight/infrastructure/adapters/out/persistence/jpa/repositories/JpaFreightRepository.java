package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.out.persistence.jpa.entities.JpaFreightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFreightRepository extends JpaRepository<JpaFreightEntity, String> {
}
