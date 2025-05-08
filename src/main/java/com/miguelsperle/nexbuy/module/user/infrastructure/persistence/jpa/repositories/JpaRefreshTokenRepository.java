package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaRefreshTokenRepository extends JpaRepository<JpaRefreshTokenEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM refresh_tokens rt WHERE rt.user_id = :userId")
    Optional<JpaRefreshTokenEntity> findByUserId(@Param("userId") String userId);
}
