package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.PaginationMetadata;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities.JpaProductEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.specifications.JpaProductSpecification;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.repositories.JpaProductRepository;
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
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public List<Product> findAll() {
        return this.jpaProductRepository.findAll().stream().map(JpaProductEntity::toEntity).toList();
    }

    @Override
    public Optional<Product> findById(String id) {
        return this.jpaProductRepository.findById(id).map(JpaProductEntity::toEntity);
    }

    @Override
    public Product save(Product product) {
        return this.jpaProductRepository.save(JpaProductEntity.from(product)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaProductRepository.deleteById(id);
    }

    @Override
    public boolean existsByBrandId(String brandId) {
        return this.jpaProductRepository.existsByBrandId(brandId);
    }

    @Override
    public boolean existsByCategoryId(String categoryId) {
        return this.jpaProductRepository.existsByCategoryId(categoryId);
    }

    @Override
    public boolean existsByColorId(String colorId) {
        return this.jpaProductRepository.existsByColorId(colorId);
    }

    @Override
    public Pagination<Product> findAllPaginated(SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaProductEntity> specification = Specification.where(null);

        final String terms = searchQuery.terms();
        final String brandId = searchQuery.filters().get("brandId");
        final String categoryId = searchQuery.filters().get("categoryId");
        final String isAdminStr = searchQuery.filters().get("isAdmin");

        final boolean isAdmin = Boolean.parseBoolean(isAdminStr);

        specification = specification.and(JpaProductSpecification.filterByTerms(terms));
        specification = specification.and(JpaProductSpecification.filterByBrandId(brandId));
        specification = specification.and(JpaProductSpecification.filterByCategoryId(categoryId));

        if (isAdmin) {
            final String status = searchQuery.filters().get("status");
            specification = specification.and(JpaProductSpecification.filterByStatus(status));
        } else {
            specification = specification.and(JpaProductSpecification.filterByStatus("ACTIVE"));
        }

        final Page<JpaProductEntity> pageResult = this.jpaProductRepository.findAll(specification, pageable);

        final List<Product> products = pageResult.getContent().stream().map(JpaProductEntity::toEntity).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(
                paginationMetadata,
                products
        );
    }
}
