package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.PaginationMetadata;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.inventory.application.ports.out.persistence.InventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.entities.JpaInventoryEntity;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.repositories.JpaInventoryRepository;
import com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.specifications.JpaInventorySpecification;
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
public class InventoryRepositoryImpl implements InventoryRepository {
    private final JpaInventoryRepository jpaInventoryRepository;

    @Override
    public List<Inventory> findAll() {
        return this.jpaInventoryRepository.findAll().stream().map(JpaInventoryEntity::toEntity).toList();
    }

    @Override
    public Optional<Inventory> findById(String id) {
        return this.jpaInventoryRepository.findById(id).map(JpaInventoryEntity::toEntity);
    }

    @Override
    public Inventory save(Inventory inventory) {
        return this.jpaInventoryRepository.save(JpaInventoryEntity.from(inventory)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaInventoryRepository.deleteById(id);
    }

    @Override
    public Optional<Inventory> findBySku(String sku) {
        return this.jpaInventoryRepository.findBySku(sku).map(JpaInventoryEntity::toEntity);
    }

    @Override
    public Pagination<Inventory> findAllPaginated(SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaInventoryEntity> specification = Specification.where(null);

        final String sku = searchQuery.filters().get("sku");

        specification = specification.and(JpaInventorySpecification.filterBySku(sku));

        final Page<JpaInventoryEntity> pageResult = this.jpaInventoryRepository.findAll(specification, pageable);

        final List<Inventory> inventories = pageResult.getContent().stream().map(JpaInventoryEntity::toEntity).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(
                paginationMetadata,
                inventories
        );
    }
}
