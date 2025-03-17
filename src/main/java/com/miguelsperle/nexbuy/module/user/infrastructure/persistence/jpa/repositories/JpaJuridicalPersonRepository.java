package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaJuridicalPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaJuridicalPersonRepository extends JpaRepository<JpaJuridicalPersonEntity, String> {
}
