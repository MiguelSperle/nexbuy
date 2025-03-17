package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<JpaUserEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM users u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<JpaUserEntity> findByEmail(@Param("email") String email);
}
