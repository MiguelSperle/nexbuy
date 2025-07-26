package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.out.persistence.jpa.entities.JpaAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaAddressRepository extends JpaRepository<JpaAddressEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM addresses ad WHERE ad.user_id = :userId")
    List<JpaAddressEntity> findAllByUserId(@Param("userId") String userId);
}
