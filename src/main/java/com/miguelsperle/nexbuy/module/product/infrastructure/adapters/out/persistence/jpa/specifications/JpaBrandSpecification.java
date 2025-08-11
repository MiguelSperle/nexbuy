package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.specifications;

import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities.JpaBrandEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaBrandSpecification {
    public static Specification<JpaBrandEntity> filterByTerms(String terms) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.like(criterialBuilder.lower(root.get("name")), "%" + terms.toLowerCase() + "%");
    }
}
