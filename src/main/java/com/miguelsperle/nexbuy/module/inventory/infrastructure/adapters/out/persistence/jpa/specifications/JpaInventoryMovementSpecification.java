package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.specifications;

import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.entities.JpaInventoryMovementEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaInventoryMovementSpecification {
    public static Specification<JpaInventoryMovementEntity> filterBySku(String sku) {
        return (root, query, criterialBuilder) ->
                (sku == null || sku.isBlank())
                        ? criterialBuilder.conjunction()
                        : criterialBuilder.equal(root.get("sku"), sku);
    }

    public static Specification<JpaInventoryMovementEntity> filterByMovementType(String movementType) {
        return (root, query, criterialBuilder) ->
                (movementType == null || movementType.isBlank())
                        ? criterialBuilder.conjunction()
                        : criterialBuilder.equal(root.get("movementType"), movementType);
    }
}
