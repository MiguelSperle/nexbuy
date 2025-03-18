package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaPhysicalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPhysicalUserRepository extends JpaRepository<JpaPhysicalUserEntity, String> {
}
