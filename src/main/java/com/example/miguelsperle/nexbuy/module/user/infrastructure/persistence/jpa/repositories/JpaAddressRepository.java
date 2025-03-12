package com.example.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.repositories;

import com.example.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities.JpaAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAddressRepository extends JpaRepository<JpaAddressEntity, String> {
}
