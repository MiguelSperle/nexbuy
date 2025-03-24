package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaUserVerificationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserVerificationCodeRepository extends JpaRepository<JpaUserVerificationCodeEntity, String> {
}
