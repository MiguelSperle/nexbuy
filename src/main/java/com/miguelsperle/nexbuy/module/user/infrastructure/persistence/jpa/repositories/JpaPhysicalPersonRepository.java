package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaPhysicalPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPhysicalPersonRepository extends JpaRepository<JpaPhysicalPersonEntity, String> {
}
