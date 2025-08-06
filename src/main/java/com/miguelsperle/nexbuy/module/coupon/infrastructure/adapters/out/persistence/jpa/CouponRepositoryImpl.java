package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.out.persistence.jpa.entities.JpaCouponEntity;
import com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.out.persistence.jpa.repositories.JpaCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {
    private final JpaCouponRepository jpaCouponRepository;

    @Override
    public List<Coupon> findAll() {
        return this.jpaCouponRepository.findAll().stream().map(JpaCouponEntity::toEntity).toList();
    }

    @Override
    public Optional<Coupon> findById(String id) {
        return this.jpaCouponRepository.findById(id).map(JpaCouponEntity::toEntity);
    }

    @Override
    public Coupon save(Coupon coupon) {
        return this.jpaCouponRepository.save(JpaCouponEntity.from(coupon)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaCouponRepository.deleteById(id);
    }

    @Override
    public boolean existsByCode(String code) {
        return this.jpaCouponRepository.existsByCode(code);
    }
}
