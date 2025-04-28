package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaUserVerificationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaUserVerificationCodeRepository extends JpaRepository<JpaUserVerificationCodeEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM user_verification_codes uvc WHERE uvc.user_id = :userId")
    Optional<JpaUserVerificationCodeEntity> findByUserId(@Param("userId") String userId);
}
