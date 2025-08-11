package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.specifications;

import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.entities.JpaInventoryMovementEntity;
import org.springframework.data.jpa.domain.Specification;

public class JpaInventoryMovementSpecification {
    public static Specification<JpaInventoryMovementEntity> filterBySku(String sku) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.like(criterialBuilder.lower(root.get("sku")), "%" + sku.toLowerCase() + "%");
    }

    public static Specification<JpaInventoryMovementEntity> filterByMovementType(InventoryMovementType movementType) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("movementType"), movementType);
    }
}
