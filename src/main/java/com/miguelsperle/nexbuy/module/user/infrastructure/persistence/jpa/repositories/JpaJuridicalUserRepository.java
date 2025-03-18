package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaJuridicalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaJuridicalUserRepository extends JpaRepository<JpaJuridicalUserEntity, String> {
}
