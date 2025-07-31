package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.specifications;

import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.entities.JpaInventoryEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaInventorySpecification {
    public static Specification<JpaInventoryEntity> filterBySku(String sku) {
        return (root, query, criterialBuilder) ->
                (sku == null || sku.isBlank())
                        ? criterialBuilder.conjunction()
                        : criterialBuilder.equal(root.get("sku"), sku);
    }
}
