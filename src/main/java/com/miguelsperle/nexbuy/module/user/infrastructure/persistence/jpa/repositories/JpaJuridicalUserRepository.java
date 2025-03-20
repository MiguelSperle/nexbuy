package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaJuridicalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaJuridicalUserRepository extends JpaRepository<JpaJuridicalUserEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM juridical_users uj WHERE uj.cnpj = :cnpj")
    Optional<JpaJuridicalUserEntity> findByCnpj(@Param("cnpj") String cnpj);

    @Query(nativeQuery = true, value = "SELECT * FROM juridical_users uj WHERE uj.legal_name = :legalName")
    Optional<JpaJuridicalUserEntity> findByLegalName(@Param("legalName") String legalName);

    @Query(nativeQuery = true, value = "SELECT * FROM juridical_users uj WHERE uj.state_registration = :stateRegistration")
    Optional<JpaJuridicalUserEntity> findByStateRegistration(@Param("stateRegistration") String stateRegistration);
}
