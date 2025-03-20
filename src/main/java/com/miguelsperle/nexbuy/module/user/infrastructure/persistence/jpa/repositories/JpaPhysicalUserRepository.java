package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaPhysicalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaPhysicalUserRepository extends JpaRepository<JpaPhysicalUserEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM physical_users up WHERE up.cpf = :cpf")
    Optional<JpaPhysicalUserEntity> findByCpf(@Param("cpf") String cpf);

    @Query(nativeQuery = true, value = "SELECT * FROM physical_users up WHERE up.general_register = :generalRegister")
    Optional<JpaPhysicalUserEntity> findByGeneralRegister(@Param("generalRegister") String generalRegister);
}
