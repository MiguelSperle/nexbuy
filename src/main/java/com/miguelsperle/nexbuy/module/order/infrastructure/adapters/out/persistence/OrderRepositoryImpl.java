package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence;

import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderRepository;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities.JpaOrderEntity;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.repositories.JpaOrderRepository;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.specifications.JpaOrderSpecification;
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
public class OrderRepositoryImpl implements OrderRepository {
    private final JpaOrderRepository jpaOrderRepository;

    @Override
    public List<Order> findAll() {
        return this.jpaOrderRepository.findAll().stream().map(JpaOrderEntity::toEntity).toList();
    }

    @Override
    public Optional<Order> findById(String id) {
        return this.jpaOrderRepository.findById(id).map(JpaOrderEntity::toEntity);
    }

    @Override
    public Order save(Order order) {
        return this.jpaOrderRepository.save(JpaOrderEntity.from(order)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaOrderRepository.deleteById(id);
    }

    @Override
    public Pagination<Order> findAllPaginatedByUserId(SearchQuery searchQuery, String userId) {
        final Sort sort = searchQuery.direction().equalsIgnoreCase("asc")
                ? Sort.by(searchQuery.sort()).ascending() : Sort.by(searchQuery.sort()).descending();

        final PageRequest pageable = PageRequest.of(searchQuery.page(), searchQuery.perPage(), sort);

        Specification<JpaOrderEntity> specification = Specification.unrestricted();

        specification = specification.and(JpaOrderSpecification.filterByUserId(userId));

        final Page<JpaOrderEntity> pageResult = this.jpaOrderRepository.findAll(specification, pageable);

        final List<Order> orders = pageResult.getContent().stream().map(JpaOrderEntity::toEntity).toList();

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return new Pagination<>(
                paginationMetadata,
                orders
        );
    }
}
