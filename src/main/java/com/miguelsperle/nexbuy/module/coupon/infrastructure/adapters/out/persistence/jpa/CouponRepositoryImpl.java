package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.domain.enums.CouponType;
import com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.out.persistence.jpa.entities.JpaCouponEntity;
import com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.out.persistence.jpa.repositories.JpaCouponRepository;
import com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.out.persistence.jpa.specifications.JpaCouponSpecification;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.PaginationMetadata;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    @Override
    public Optional<Coupon> findByCode(String code) {
        return this.jpaCouponRepository.findByCode(code).map(JpaCouponEntity::toEntity);
    }

    @Override
    public Pagination<Coupon> findAllPaginated(SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaCouponEntity> specification = Specification.where(null);

        final String percentageStr = searchQuery.filters().get("percentage");

        if (percentageStr != null && !percentageStr.isBlank()) {
            final int percentage = Integer.parseInt(percentageStr);
            specification = specification.and(JpaCouponSpecification.filterByPercentage(percentage));
        }

        final String minimumPurchaseAmountStr = searchQuery.filters().get("minimumPurchaseAmount");

        if (minimumPurchaseAmountStr != null && minimumPurchaseAmountStr.isBlank()) {
            final BigDecimal minimumPurchaseAmount = new BigDecimal(minimumPurchaseAmountStr);
            specification = specification.and(JpaCouponSpecification.filterByMinimumPurchaseAmount(minimumPurchaseAmount));
        }

        final String typeStr = searchQuery.filters().get("type");

        if (typeStr != null && !typeStr.isBlank()) {
            final CouponType couponType = CouponType.valueOf(typeStr);
            specification = specification.and(JpaCouponSpecification.filterByType(couponType));
        }

        final boolean isAdmin = Boolean.parseBoolean(searchQuery.filters().getOrDefault("isAdmin", "false"));

        if (isAdmin) {
            final String isActiveStr = searchQuery.filters().get("isActive");
            if (isActiveStr != null && !isActiveStr.isBlank()) {
                final boolean isActive = Boolean.parseBoolean(isActiveStr);
                specification = specification.and(JpaCouponSpecification.filterByIsActive(isActive));
            }
        } else {
            specification = specification.and(JpaCouponSpecification.filterByIsActive(true));
        }

        final Page<JpaCouponEntity> pageResult = this.jpaCouponRepository.findAll(specification, pageable);

        final List<Coupon> coupons = pageResult.getContent().stream().map(JpaCouponEntity::toEntity).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(
                paginationMetadata,
                coupons
        );
    }
}
