package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence;

import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.entities.JpaColorEntity;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.repositories.JpaColorRepository;
import com.miguelsperle.nexbuy.module.product.infrastructure.adapters.out.persistence.jpa.specifications.JpaColorSpecification;
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
public class ColorRepositoryImpl implements ColorRepository {
    private final JpaColorRepository jpaColorRepository;

    @Override
    public List<Color> findAll() {
        return this.jpaColorRepository.findAll().stream().map(JpaColorEntity::toEntity).toList();
    }

    @Override
    public Optional<Color> findById(String id) {
        return this.jpaColorRepository.findById(id).map(JpaColorEntity::toEntity);
    }

    @Override
    public Color save(Color color) {
        return this.jpaColorRepository.save(JpaColorEntity.from(color)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaColorRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return this.jpaColorRepository.existsByName(name);
    }

    @Override
    public Pagination<Color> findAllPaginated(SearchQuery searchQuery) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaColorEntity> specification = Specification.unrestricted();

        final String terms = searchQuery.terms();

        if (!terms.isBlank()) {
            specification = specification.and(JpaColorSpecification.filterByTerms(terms));
        }

        final Page<JpaColorEntity> pageResult = this.jpaColorRepository.findAll(specification, pageable);

        final List<Color> colors = pageResult.getContent().stream().map(JpaColorEntity::toEntity).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(
                paginationMetadata,
                colors
        );
    }
}
