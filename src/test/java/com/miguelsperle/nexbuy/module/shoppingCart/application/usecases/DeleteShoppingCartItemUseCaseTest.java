package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.DeleteShoppingCartItemUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.module.shoppingCart.utils.ShoppingCartBuilderTest;
import com.miguelsperle.nexbuy.module.shoppingCart.utils.ShoppingCartItemBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeleteShoppingCartItemUseCaseTest {
    @InjectMocks
    private DeleteShoppingCartItemUseCaseImpl deleteShoppingCartItemUseCase;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Test
    @DisplayName("Should delete shopping cart item")
    public void should_delete_shopping_cart_item() {
        final ShoppingCart shoppingCart = ShoppingCartBuilderTest.create();
        final ShoppingCartItem shoppingCartItem = ShoppingCartItemBuilderTest.create(shoppingCart.getId());

        Mockito.when(this.shoppingCartRepository.findById(Mockito.any())).thenReturn(Optional.of(shoppingCart));

        Mockito.when(this.shoppingCartItemRepository.findById(Mockito.any())).thenReturn(Optional.of(shoppingCartItem));

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        Mockito.doNothing().when(this.shoppingCartItemRepository).deleteById(Mockito.any());

        Mockito.when(this.shoppingCartItemRepository.findAllByShoppingCartId(Mockito.any())).thenReturn(List.of(shoppingCartItem));

        final ShoppingCart updatedShoppingCart = shoppingCart.withTotalAmount(DecimalUtils.valueOf(50));

        Mockito.when(this.shoppingCartRepository.save(Mockito.any())).thenReturn(updatedShoppingCart);

        final DeleteShoppingCartItemUseCaseInput deleteShoppingCartItemUseCaseInput = DeleteShoppingCartItemUseCaseInput.with(
                shoppingCart.getId(),
                shoppingCartItem.getShoppingCartId()
        );

        this.deleteShoppingCartItemUseCase.execute(deleteShoppingCartItemUseCaseInput);

        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.shoppingCartItemRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.shoppingCartItemRepository, Mockito.times(1)).deleteById(Mockito.any());
        Mockito.verify(this.shoppingCartItemRepository, Mockito.times(1)).findAllByShoppingCartId(Mockito.any());
        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when shopping cart does not exist")
    public void should_throw_NotFoundException_when_shopping_cart_does_not_exist() {
        final ShoppingCart shoppingCart = ShoppingCartBuilderTest.create();
        final ShoppingCartItem shoppingCartItem = ShoppingCartItemBuilderTest.create(shoppingCart.getId());

        Mockito.when(this.shoppingCartRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DeleteShoppingCartItemUseCaseInput deleteShoppingCartItemUseCaseInput = DeleteShoppingCartItemUseCaseInput.with(
                shoppingCart.getId(),
                shoppingCartItem.getShoppingCartId()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.deleteShoppingCartItemUseCase.execute(deleteShoppingCartItemUseCaseInput)
        );

        final String expectedErrorMessage = "Shopping cart not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when shopping cart item does not exist")
    public void should_throw_NotFoundException_when_shopping_cart_item_does_not_exist() {
        final ShoppingCart shoppingCart = ShoppingCartBuilderTest.create();
        final ShoppingCartItem shoppingCartItem = ShoppingCartItemBuilderTest.create(shoppingCart.getId());

        Mockito.when(this.shoppingCartRepository.findById(Mockito.any())).thenReturn(Optional.of(shoppingCart));

        Mockito.when(this.shoppingCartItemRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final DeleteShoppingCartItemUseCaseInput deleteShoppingCartItemUseCaseInput = DeleteShoppingCartItemUseCaseInput.with(
                shoppingCart.getId(),
                shoppingCartItem.getShoppingCartId()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.deleteShoppingCartItemUseCase.execute(deleteShoppingCartItemUseCaseInput)
        );

        final String expectedErrorMessage = "Shopping cart item not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.shoppingCartItemRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
