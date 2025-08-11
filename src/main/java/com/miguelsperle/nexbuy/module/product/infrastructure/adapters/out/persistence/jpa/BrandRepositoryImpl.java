package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.BrandRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities.JpaBrandEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.repositories.JpaBrandRepository;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.specifications.JpaBrandSpecification;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.PaginationMetadata;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
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
public class BrandRepositoryImpl implements BrandRepository {
    private final JpaBrandRepository jpaBrandRepository;

    @Override
    public List<Brand> findAll() {
        return this.jpaBrandRepository.findAll().stream().map(JpaBrandEntity::toEntity).toList();
    }

    @Override
    public Optional<Brand> findById(String id) {
        return this.jpaBrandRepository.findById(id).map(JpaBrandEntity::toEntity);
    }

    @Override
    public Brand save(Brand brand) {
        return this.jpaBrandRepository.save(JpaBrandEntity.from(brand)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaBrandRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return this.jpaBrandRepository.existsByName(name);
    }

    @Override
    public Pagination<Brand> findAllPaginated(SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaBrandEntity> specification = Specification.where(null);

        final String terms = searchQuery.terms();

        if (!terms.isBlank()) {
            specification = specification.and(JpaBrandSpecification.filterByTerms(terms));
        }

        final Page<JpaBrandEntity> pageResult = this.jpaBrandRepository.findAll(specification, pageable);

        final List<Brand> brands = pageResult.getContent().stream().map(JpaBrandEntity::toEntity).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(
                paginationMetadata,
                brands
        );
    }
}
