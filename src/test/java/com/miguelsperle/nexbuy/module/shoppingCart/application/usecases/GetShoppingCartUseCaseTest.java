package com.miguelsperle.nexbuy.module.shoppingCart.application.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartItemRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.ports.out.persistence.ShoppingCartRepository;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.outputs.GetShoppingCartUseCaseOutput;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCart;
import com.miguelsperle.nexbuy.module.shoppingCart.domain.entities.ShoppingCartItem;
import com.miguelsperle.nexbuy.module.shoppingCart.utils.ShoppingCartBuilderTest;
import com.miguelsperle.nexbuy.module.shoppingCart.utils.ShoppingCartItemBuilderTest;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
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
public class GetShoppingCartUseCaseTest {
    @InjectMocks
    private GetShoppingCartUseCaseImpl getShoppingCartUseCase;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Mock
    private SecurityContextService securityContextService;

    @Test
    @DisplayName("Should get shopping cart")
    public void should_get_shopping_cart() {
        final ShoppingCart shoppingCart = ShoppingCartBuilderTest.create();
        final ShoppingCartItem shoppingCartItem = ShoppingCartItemBuilderTest.create(shoppingCart.getId());
        final String authenticatedUserId = IdentifierUtils.generateUUID();

        final List<ShoppingCartItem> shoppingCartItems = List.of(shoppingCartItem);

        Mockito.when(this.securityContextService.getAuthenticatedUserId()).thenReturn(authenticatedUserId);

        Mockito.when(this.shoppingCartRepository.findByUserId(Mockito.any())).thenReturn(Optional.of(shoppingCart));

        Mockito.when(this.shoppingCartItemRepository.findAllByShoppingCartId(Mockito.any())).thenReturn(shoppingCartItems);

        final GetShoppingCartUseCaseOutput getShoppingCartUseCaseOutput = this.getShoppingCartUseCase.execute();

        Assertions.assertNotNull(getShoppingCartUseCaseOutput);
        Assertions.assertNotNull(getShoppingCartUseCaseOutput.shoppingCart());
        Assertions.assertNotNull(getShoppingCartUseCaseOutput.shoppingCartItems());

        Assertions.assertEquals(shoppingCart, getShoppingCartUseCaseOutput.shoppingCart());
        Assertions.assertEquals(shoppingCartItems, getShoppingCartUseCaseOutput.shoppingCartItems());

        Mockito.verify(this.securityContextService, Mockito.times(1)).getAuthenticatedUserId();
        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).findByUserId(Mockito.any());
        Mockito.verify(this.shoppingCartItemRepository, Mockito.times(1)).findAllByShoppingCartId(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when shopping cart does not exist")
    public void should_throw_NotFoundException_when_shopping_cart_does_not_exist() {
        Mockito.when(this.shoppingCartRepository.findByUserId(Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.getShoppingCartUseCase.execute()
        );

        final String expectedErrorMessage = "Shopping cart not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.shoppingCartRepository, Mockito.times(1)).findByUserId(Mockito.any());
    }
}
