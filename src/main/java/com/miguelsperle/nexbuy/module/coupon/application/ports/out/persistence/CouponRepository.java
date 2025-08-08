package com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponRepository {
    List<Coupon> findAll();
    Optional<Coupon> findById(String id);
    Coupon save(Coupon coupon);
    void deleteById(String id);
    boolean existsByCode(String code);
    Optional<Coupon> findByCode(String code);
}
