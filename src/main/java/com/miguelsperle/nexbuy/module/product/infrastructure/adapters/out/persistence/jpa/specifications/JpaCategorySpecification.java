package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.specifications;

import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities.JpaCategoryEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaCategorySpecification {
    public static Specification<JpaCategoryEntity> filterByTerms(String terms) {
        return (root, query, criterialBuilder) ->
                (terms == null || terms.isBlank())
                        ? criterialBuilder.conjunction()
                        : criterialBuilder.like(criterialBuilder.lower(root.get("name")), "%" + terms.toLowerCase() + "%");
    }
}
