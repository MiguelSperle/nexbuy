package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.UpdateUserUseCaseInput;
import com.miguelsperle.nexbuy.core.application.exceptions.AuthenticatedUserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IUpdateUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public class UpdateUserUseCase implements IUpdateUserUseCase {
    private final IAuthenticatedUserService authenticatedUserService;
    private final IUserGateway userGateway;

    public UpdateUserUseCase(
            IAuthenticatedUserService authenticatedUserService,
            IUserGateway userGateway
    ) {
        this.authenticatedUserService = authenticatedUserService;
        this.userGateway = userGateway;
    }

    @Override
    public void execute(UpdateUserUseCaseInput updateUserUseCaseInput) {
        final User authenticatedUser = this.getAuthenticatedUser();

        final User updatedAuthenticatedUser = authenticatedUser.withFirstName(updateUserUseCaseInput.firstName())
                .withLastName(updateUserUseCaseInput.lastName())
                .withPhoneNumber(updateUserUseCaseInput.phoneNumber());

        this.saveUser(updatedAuthenticatedUser);
    }

    private User getAuthenticatedUser() {
        return this.authenticatedUserService.getAuthenticatedUser()
                .orElseThrow(() -> new AuthenticatedUserNotFoundException("Authenticated user not found in security context"));
    }

    private void saveUser(User user) {
        this.userGateway.save(user);
    }
}
