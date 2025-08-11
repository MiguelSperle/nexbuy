package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.specifications;

import com.miguelsperle.nexbuy.module.product.domain.enums.ProductStatus;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities.JpaProductEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaProductSpecification {
    public static Specification<JpaProductEntity> filterByCategoryId(String categoryId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("categoryId"), categoryId);
    }

    public static Specification<JpaProductEntity> filterByBrandId(String brandId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("brandId"), brandId);
    }

    public static Specification<JpaProductEntity> filterByTerms(String terms) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.or(
                        criterialBuilder.like(criterialBuilder.lower(root.get("name")), "%" + terms.toLowerCase() + "%"),
                        criterialBuilder.like(criterialBuilder.lower(root.get("description")), "%" + terms.toLowerCase() + "%")
                );
    }

    public static Specification<JpaProductEntity> filterByProductStatus(ProductStatus productStatus) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("productStatus"), productStatus);
    }
}
