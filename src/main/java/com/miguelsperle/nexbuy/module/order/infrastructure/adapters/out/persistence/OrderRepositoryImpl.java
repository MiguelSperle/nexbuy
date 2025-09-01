package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence;

import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderRepository;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities.JpaOrderEntity;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.repositories.JpaOrderRepository;
import lombok.RequiredArgsConstructor;
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
}
