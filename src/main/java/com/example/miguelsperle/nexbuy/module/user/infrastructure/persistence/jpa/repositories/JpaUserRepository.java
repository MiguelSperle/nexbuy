package com.example.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.example.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<JpaUserEntity, String> {
}
