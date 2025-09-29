package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.DeleteAllShoppingCartItemsUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.module.shoppingCart.utils.ShoppingCartBuilderTest;
import com.miguelsperle.nexbuy.module.shoppingCart.utils.ShoppingCartItemBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeleteAllShoppingCartItemsUseCaseTest {
    @InjectMocks
    private DeleteAllShoppingCartItemsUseCaseImpl deleteAllShoppingCartItemsUseCase;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Test
    @DisplayName("Should delete all shopping cart items")
    public void should_delete_all_shopping_cart_items() {
        final ShoppingCart shoppingCart = ShoppingCartBuilderTest.create();
        final ShoppingCartItem shoppingCartItem = ShoppingCartItemBuilderTest.create(shoppingCart.getId());

        Mockito.when(this.shoppingCartRepository.findById(Mockito.any())).thenReturn(Optional.of(shoppingCart));

        Mockito.when(this.shoppingCartItemRepository.findAllByShoppingCartId(Mockito.any())).thenReturn(List.of(shoppingCartItem));

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        Mockito.doNothing().when(this.shoppingCartItemRepository).deleteAll(Mockito.any());

        final ShoppingCart updatedShoppingCart = shoppingCart.withTotalAmount(BigDecimal.ZERO);

        Mockito.when(this.shoppingCartRepository.save(Mockito.any())).thenReturn(updatedShoppingCart);

        final DeleteAllShoppingCartItemsUseCaseInput deleteAllShoppingCartItemsUseCaseInput = DeleteAllShoppingCartItemsUseCaseInput.with(
                shoppingCart.getId()
        );

        this.deleteAllShoppingCartItemsUseCase.execute(deleteAllShoppingCartItemsUseCaseInput);

        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.shoppingCartItemRepository, Mockito.times(1)).findAllByShoppingCartId(Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.shoppingCartItemRepository, Mockito.times(1)).deleteAll(Mockito.any());
        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when shopping cart does not exist")
    public void should_throw_NotFoundException_when_shopping_cart_does_not_exist() {
        final ShoppingCart shoppingCart = ShoppingCartBuilderTest.create();

        Mockito.when(this.shoppingCartRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DeleteAllShoppingCartItemsUseCaseInput deleteAllShoppingCartItemsUseCaseInput = DeleteAllShoppingCartItemsUseCaseInput.with(
                shoppingCart.getId()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.deleteAllShoppingCartItemsUseCase.execute(deleteAllShoppingCartItemsUseCaseInput)
        );

        final String expectedErrorMessage = "Shopping cart not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
