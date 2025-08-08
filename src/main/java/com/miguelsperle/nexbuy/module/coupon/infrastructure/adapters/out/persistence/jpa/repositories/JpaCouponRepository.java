package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.out.persistence.jpa.repositories;

import com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.out.persistence.jpa.entities.JpaCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaCouponRepository extends JpaRepository<JpaCouponEntity, String> {
    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT 1 FROM coupons c WHERE c.code = :code)")
    boolean existsByCode(@Param("code") String code);

    @Query(nativeQuery = true, value = "SELECT * FROM coupons c WHERE c.code = :code")
    Optional<JpaCouponEntity> findByCode(@Param("code") String code);
}
