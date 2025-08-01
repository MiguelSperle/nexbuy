package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.specifications;

import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities.JpaProductEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaProductSpecification {
    public static Specification<JpaProductEntity> filterByCategoryId(String categoryId) {
        return (root, query, criteriaBuilder) ->
                (categoryId == null || categoryId.isBlank())
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("categoryId"), categoryId);
    }

    public static Specification<JpaProductEntity> filterByBrandId(String brandId) {
        return (root, query, criteriaBuilder) ->
                (brandId == null || brandId.isBlank())
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("brandId"), brandId);
    }

    public static Specification<JpaProductEntity> filterByTerms(String terms) {
        return (root, query, criterialBuilder) ->
                (terms == null || terms.isBlank())
                        ? criterialBuilder.conjunction()
                        : criterialBuilder.or(
                        criterialBuilder.like(criterialBuilder.lower(root.get("name")), "%" + terms.toLowerCase() + "%"),
                        criterialBuilder.like(criterialBuilder.lower(root.get("description")), "%" + terms.toLowerCase() + "%")
                );
    }

    public static Specification<JpaProductEntity> filterByStatus(String status) {
        return (root, query, criterialBuilder) ->
                (status == null || status.isBlank())
                        ? criterialBuilder.conjunction()
                        : criterialBuilder.equal(criterialBuilder.lower(root.get("productStatus")), status.toLowerCase());
    }
}
