package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.PaginationMetadata;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.entities.JpaInventoryMovementEntity;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.repositories.JpaInventoryMovementRepository;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.specifications.JpaInventoryMovementSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventoryMovementRepositoryImpl implements InventoryMovementRepository {
    private final JpaInventoryMovementRepository jpaInventoryMovementRepository;

    @Override
    public List<InventoryMovement> findAll() {
        return this.jpaInventoryMovementRepository.findAll().stream().map(JpaInventoryMovementEntity::toEntity).toList();
    }

    @Override
    public Optional<InventoryMovement> findById(String id) {
        return this.jpaInventoryMovementRepository.findById(id).map(JpaInventoryMovementEntity::toEntity);
    }

    @Override
    public InventoryMovement save(InventoryMovement inventoryMovement) {
        return this.jpaInventoryMovementRepository.save(JpaInventoryMovementEntity.from(inventoryMovement)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaInventoryMovementRepository.deleteById(id);
    }

    @Override
    public Pagination<InventoryMovement> findAllPaginatedBySku(String sku, SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaInventoryMovementEntity> specification = Specification.where(JpaInventoryMovementSpecification.filterBySku(sku));

        final String movementType = searchQuery.filters().get("movementType");

        specification = specification.and(JpaInventoryMovementSpecification.filterByMovementType(movementType));

        final Page<JpaInventoryMovementEntity> pageResult = this.jpaInventoryMovementRepository.findAll(specification, pageable);

        final List<InventoryMovement> inventoryMovements = pageResult.getContent().stream().map(JpaInventoryMovementEntity::toEntity).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(
                paginationMetadata,
                inventoryMovements
        );
    }
}
