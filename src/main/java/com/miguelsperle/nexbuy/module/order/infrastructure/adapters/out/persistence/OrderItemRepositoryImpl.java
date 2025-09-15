package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence;

import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderItemRepository;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderItem;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities.JpaOrderItemEntity;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.repositories.JpaOrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final JpaOrderItemRepository jpaOrderItemRepository;

    @Override
    public List<OrderItem> findAll() {
        return this.jpaOrderItemRepository.findAll().stream().map(JpaOrderItemEntity::toEntity).toList();
    }

    @Override
    public Optional<OrderItem> findById(String id) {
        return this.jpaOrderItemRepository.findById(id).map(JpaOrderItemEntity::toEntity);
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return this.jpaOrderItemRepository.save(JpaOrderItemEntity.from(orderItem)).toEntity();
    }

    @Override
    public List<OrderItem> saveAll(List<OrderItem> orderItems) {
        return this.jpaOrderItemRepository.saveAll(orderItems.stream().map(JpaOrderItemEntity::from)
                .toList()).stream().map(JpaOrderItemEntity::toEntity).toList();
    }

    @Override
    public void deleteById(String id) {
        this.jpaOrderItemRepository.deleteById(id);
    }

    @Override
    public List<OrderItem> findAllByOrderId(String orderId) {
        return this.jpaOrderItemRepository.findAllByOrderId(orderId).stream().map(JpaOrderItemEntity::toEntity).toList();
    }
}
