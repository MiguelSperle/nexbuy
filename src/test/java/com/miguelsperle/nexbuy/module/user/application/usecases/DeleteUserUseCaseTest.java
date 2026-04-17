package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.utils.UserBuilderTest;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityService;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeleteUserUseCaseTest {
    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityService securityService;

    @Test
    @DisplayName("Should delete user when it exists")
    public void should_delete_user_when_it_exists() {
        final User user = UserBuilderTest.create(
                UserStatus.VERIFIED, AuthorizationRole.CUSTOMER, PersonType.NATURAL_PERSON
        );

        Mockito.when(this.securityService.getUserId()).thenReturn(user.getId());

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        final User updatedUser = user.withUserStatus(UserStatus.DELETED);

        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(updatedUser);

        this.deleteUserUseCase.execute();

        Mockito.verify(this.securityService, Mockito.times(1)).getUserId();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user does not exist")
    public void should_throw_NotFoundException_when_user_does_not_exist() {
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () ->
                this.deleteUserUseCase.execute()
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, exception);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
