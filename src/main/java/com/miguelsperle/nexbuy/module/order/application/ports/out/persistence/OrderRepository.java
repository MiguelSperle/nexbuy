package com.miguelsperle.nexbuy.module.order.application.ports.out.persistence;

import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.domain.enums.OrderStatus;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll();
    Optional<Order> findById(String id);
    Order save(Order order);
    void deleteById(String id);
    Pagination<Order> findAllPaginatedByUserId(SearchQuery searchQuery, String userId);
    List<Order> findAllOrdersByStatus(OrderStatus orderStatus);
}
