package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.CategoryRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities.JpaCategoryEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.repositories.JpaCategoryRepository;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.specifications.JpaCategorySpecification;
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
public class CategoryRepositoryImpl implements CategoryRepository {
    private final JpaCategoryRepository jpaCategoryRepository;

    @Override
    public List<Category> findAll() {
        return this.jpaCategoryRepository.findAll().stream().map(JpaCategoryEntity::toEntity).toList();
    }

    @Override
    public Optional<Category> findById(String id) {
        return this.jpaCategoryRepository.findById(id).map(JpaCategoryEntity::toEntity);
    }

    @Override
    public Category save(Category category) {
        return this.jpaCategoryRepository.save(JpaCategoryEntity.from(category)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaCategoryRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return this.jpaCategoryRepository.existsByName(name);
    }

    @Override
    public Pagination<Category> findAllPaginated(SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaCategoryEntity> specification = Specification.unrestricted();

        final String terms = searchQuery.terms();

        if (!terms.isBlank()) {
            specification = specification.and(JpaCategorySpecification.filterByTerms(terms));
        }

        final Page<JpaCategoryEntity> pageResult = this.jpaCategoryRepository.findAll(specification, pageable);

        final List<Category> categories = pageResult.getContent().stream().map(JpaCategoryEntity::toEntity).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(
                paginationMetadata,
                categories
        );
    }
}
