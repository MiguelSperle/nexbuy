package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.specifications;

import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities.JpaOrderEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaOrderSpecification {
    public static Specification<JpaOrderEntity> filterByUserId(String userId) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("userId"), userId);
    }
}
