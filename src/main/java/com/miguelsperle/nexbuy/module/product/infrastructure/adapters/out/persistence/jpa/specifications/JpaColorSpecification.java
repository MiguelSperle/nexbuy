package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.specifications;

import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities.JpaColorEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaColorSpecification {
    public static Specification<JpaColorEntity> filterByTerms(String terms) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.like(criterialBuilder.lower(root.get("name")), "%" + terms.toLowerCase() + "%");
    }
}
