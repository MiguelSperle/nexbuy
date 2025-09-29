package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.AddItemToShoppingCartUseCaseInput;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AddItemToShoppingCartBuilderTestUseCaseTest {
    @InjectMocks
    private AddItemToShoppingCartUseCaseImpl addItemToShoppingCartUseCase;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Test
    @DisplayName("Should add item to shopping cart for the first time")
    public void should_add_item_to_shopping_cart_for_the_first_time() {
        final ShoppingCart shoppingCart = ShoppingCartBuilderTest.create();
        final ShoppingCartItem shoppingCartItem = ShoppingCartItemBuilderTest.create(
                shoppingCart.getId(), 1, DecimalUtils.valueOf(100)
        );
        final BigDecimal newTotalAmount = DecimalUtils.valueOf(150);

        Mockito.when(this.shoppingCartRepository.findById(Mockito.any())).thenReturn(Optional.of(shoppingCart));

        Mockito.when(this.shoppingCartItemRepository.findByShoppingCartIdAndProductId(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        Mockito.when(this.shoppingCartItemRepository.save(Mockito.any())).thenReturn(shoppingCartItem);

        Mockito.when(this.shoppingCartItemRepository.findAllByShoppingCartId(Mockito.any())).thenReturn(List.of(shoppingCartItem));

        final ShoppingCart updatedShoppingCart = shoppingCart.withTotalAmount(newTotalAmount);

        Mockito.when(this.shoppingCartRepository.save(Mockito.any())).thenReturn(updatedShoppingCart);

        final AddItemToShoppingCartUseCaseInput addItemToShoppingCartUseCaseInput = AddItemToShoppingCartUseCaseInput.with(
                shoppingCart.getId(),
                shoppingCartItem.getProductId(),
                shoppingCartItem.getUnitPrice()
        );

        this.addItemToShoppingCartUseCase.execute(addItemToShoppingCartUseCaseInput);

        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.shoppingCartItemRepository, Mockito.times(1)).findByShoppingCartIdAndProductId(Mockito.any(), Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.shoppingCartItemRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.shoppingCartItemRepository, Mockito.times(1)).findAllByShoppingCartId(Mockito.any());
        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should add item to shopping cart again")
    public void should_add_item_to_shopping_cart_again() {
        final ShoppingCart shoppingCart = ShoppingCartBuilderTest.create();
        final ShoppingCartItem shoppingCartItem = ShoppingCartItemBuilderTest.create(
                shoppingCart.getId(), 1, DecimalUtils.valueOf(100)
        );
        final BigDecimal newTotalAmount = DecimalUtils.valueOf(150);

        Mockito.when(this.shoppingCartRepository.findById(Mockito.any())).thenReturn(Optional.of(shoppingCart));

        Mockito.when(this.shoppingCartItemRepository.findByShoppingCartIdAndProductId(Mockito.any(), Mockito.any())).thenReturn(Optional.of(shoppingCartItem));

        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        final ShoppingCartItem updatedShoppingCartItem = shoppingCartItem.withQuantity(2);

        Mockito.when(this.shoppingCartItemRepository.save(Mockito.any())).thenReturn(updatedShoppingCartItem);

        final ShoppingCart updatedShoppingCart = shoppingCart.withTotalAmount(newTotalAmount);

        Mockito.when(this.shoppingCartRepository.save(Mockito.any())).thenReturn(updatedShoppingCart);

        final AddItemToShoppingCartUseCaseInput addItemToShoppingCartUseCaseInput = AddItemToShoppingCartUseCaseInput.with(
                shoppingCart.getId(),
                shoppingCartItem.getProductId(),
                shoppingCartItem.getUnitPrice()
        );

        this.addItemToShoppingCartUseCase.execute(addItemToShoppingCartUseCaseInput);

        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.shoppingCartItemRepository, Mockito.times(1)).findByShoppingCartIdAndProductId(Mockito.any(), Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.shoppingCartItemRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when shopping cart does not exist")
    public void should_throw_NotFoundException_when_shopping_cart_does_not_exist() {
        final ShoppingCart shoppingCart = ShoppingCartBuilderTest.create();
        final ShoppingCartItem shoppingCartItem = ShoppingCartItemBuilderTest.create(
                shoppingCart.getId(), 1, DecimalUtils.valueOf(100)
        );

        Mockito.when(this.shoppingCartRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final AddItemToShoppingCartUseCaseInput addItemToShoppingCartUseCaseInput = AddItemToShoppingCartUseCaseInput.with(
                shoppingCart.getId(),
                shoppingCartItem.getProductId(),
                shoppingCartItem.getUnitPrice()
        );

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.addItemToShoppingCartUseCase.execute(addItemToShoppingCartUseCaseInput)
        );

        final String expectedErrorMessage = "Shopping cart not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
