package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaLegalPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaLegalPersonRepository extends JpaRepository<JpaLegalPersonEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM legal_persons lp WHERE lp.cnpj = :cnpj")
    Optional<JpaLegalPersonEntity> findByCnpj(@Param("cnpj") String cnpj);

    @Query(nativeQuery = true, value = "SELECT * FROM legal_persons lp WHERE lp.fantasy_name = :fantasyName")
    Optional<JpaLegalPersonEntity> findByFantasyName(@Param("fantasyName") String fantasyName);

    @Query(nativeQuery = true, value = "SELECT * FROM legal_persons lp WHERE lp.legal_name = :legalName")
    Optional<JpaLegalPersonEntity> findByLegalName(@Param("legalName") String legalName);

    @Query(nativeQuery = true, value = "SELECT * FROM legal_persons lp WHERE lp.state_registration = :stateRegistration")
    Optional<JpaLegalPersonEntity> findByStateRegistration(@Param("stateRegistration") String stateRegistration);

    @Query(nativeQuery = true, value = "SELECT * FROM legal_persons lp WHERE lp.user_id = :userId")
    Optional<JpaLegalPersonEntity> findByUserId(@Param("userId") String userId);
}
