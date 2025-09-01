package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence;

import com.miguelsperle.nexbuy.module.order.application.ports.out.persistence.OrderDeliveryRepository;
import com.miguelsperle.nexbuy.module.order.domain.entities.OrderDelivery;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities.JpaOrderDeliveryEntity;
import com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.repositories.JpaOrderDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderDeliveryRepositoryImpl implements OrderDeliveryRepository {
    private final JpaOrderDeliveryRepository jpaOrderDeliveryRepository;

    @Override
    public List<OrderDelivery> findAll() {
        return this.jpaOrderDeliveryRepository.findAll().stream().map(JpaOrderDeliveryEntity::toEntity).toList();
    }

    @Override
    public Optional<OrderDelivery> findById(String id) {
        return this.jpaOrderDeliveryRepository.findById(id).map(JpaOrderDeliveryEntity::toEntity);
    }

    @Override
    public OrderDelivery save(OrderDelivery orderDelivery) {
        return this.jpaOrderDeliveryRepository.save(JpaOrderDeliveryEntity.from(orderDelivery)).toEntity();
    }

    @Override
    public void deleteById(String id) {
        this.jpaOrderDeliveryRepository.deleteById(id);
    }
}
