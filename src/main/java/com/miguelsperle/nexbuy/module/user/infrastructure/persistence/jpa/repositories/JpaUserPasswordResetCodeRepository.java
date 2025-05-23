package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaUserPasswordResetCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserPasswordResetCodeRepository extends JpaRepository<JpaUserPasswordResetCodeEntity, String> {
}
