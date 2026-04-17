package com.miguelsperle.nexbuy.module.order.application.usecases;

import com.miguelsperle.nexbuy.module.order.application.abstractions.repositories.OrderRepository;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.GetOrdersUseCaseInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs.GetOrdersUseCaseOutput;
import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.utils.OrderBuilderTest;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityService;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.PaginationMetadata;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GetOrdersUseCaseTest {
    @InjectMocks
    private GetOrdersUseCaseImpl getOrdersUseCase;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SecurityService securityService;

    @Test
    @DisplayName("Should get orders")
    public void should_get_orders() {
        final Order order = OrderBuilderTest.create();

        Mockito.when(this.securityService.getUserId()).thenReturn(order.getUserId());

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(1, 10, "name", "asc");

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), 1, List.of(order).size()
        );

        final Pagination<Order> paginatedOrders = new Pagination<>(
                paginationMetadata, List.of(order)
        );

        Mockito.when(this.orderRepository.findAllPaginatedByUserId(Mockito.any(), Mockito.any())).thenReturn(paginatedOrders);

        final GetOrdersUseCaseInput getOrdersUseCaseInput = GetOrdersUseCaseInput.with(searchQuery);

        final GetOrdersUseCaseOutput getOrdersUseCaseOutput = this.getOrdersUseCase.execute(getOrdersUseCaseInput);

        Assertions.assertNotNull(getOrdersUseCaseOutput);
        Assertions.assertNotNull(getOrdersUseCaseOutput.paginatedOrders());

        Assertions.assertEquals(paginatedOrders, getOrdersUseCaseOutput.paginatedOrders());
        Assertions.assertEquals(1, getOrdersUseCaseOutput.paginatedOrders().items().size());
        Assertions.assertEquals(paginationMetadata, getOrdersUseCaseOutput.paginatedOrders().paginationMetadata());

        Mockito.verify(this.securityService, Mockito.times(1)).getUserId();
        Mockito.verify(this.orderRepository, Mockito.times(1)).findAllPaginatedByUserId(Mockito.any(), Mockito.any());
    }
}
