package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.UpdateUserPasswordUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.InvalidCurrentPasswordException;
import com.miguelsperle.nexbuy.core.application.exceptions.AuthenticatedUserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IUpdateUserPasswordUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public class UpdateUserPasswordUseCase implements IUpdateUserPasswordUseCase {
    private final IAuthenticatedUserService authenticatedUserService;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final IUserGateway userGateway;

    public UpdateUserPasswordUseCase(
            IAuthenticatedUserService authenticatedUserService,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            IUserGateway userGateway
    ) {
        this.authenticatedUserService = authenticatedUserService;
        this.passwordEncryptorProvider = passwordEncryptorProvider;
        this.userGateway = userGateway;
    }

    @Override
    public void execute(UpdateUserPasswordUseCaseInput updateUserPasswordUseCaseInput) {
        final User authenticatedUser = this.getAuthenticatedUser();

        if (!this.validatePassword(updateUserPasswordUseCaseInput.currentPassword(), authenticatedUser.getPassword())) {
            throw new InvalidCurrentPasswordException("Invalid current password");
        }

        final String encodedPassword = this.passwordEncryptorProvider.encode(updateUserPasswordUseCaseInput.password());

        final User authenticatedUserUpdated = authenticatedUser.withPassword(encodedPassword);

        this.userGateway.save(authenticatedUserUpdated);
    }

    private boolean validatePassword(String password, String encodedPassword) {
        return this.passwordEncryptorProvider.matches(password, encodedPassword);
    }

    private User getAuthenticatedUser() {
        return this.authenticatedUserService.getAuthenticatedUser()
                .orElseThrow(() -> new AuthenticatedUserNotFoundException("Authenticated user not found in security context"));
    }
}
