package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.ISecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IUpdateUserUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public class UpdateUserUseCase implements IUpdateUserUseCase {
    private final ISecurityContextService authenticatedUserService;
    private final IUserGateway userGateway;

    public UpdateUserUseCase(
            ISecurityContextService authenticatedUserService,
            IUserGateway userGateway
    ) {
        this.authenticatedUserService = authenticatedUserService;
        this.userGateway = userGateway;
    }

    @Override
    public void execute(UpdateUserUseCaseInput updateUserUseCaseInput) {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final User user = this.getUserById(authenticatedUserId);

        final User updatedAuthenticatedUser = user.withFirstName(updateUserUseCaseInput.firstName())
                .withLastName(updateUserUseCaseInput.lastName())
                .withPhoneNumber(updateUserUseCaseInput.phoneNumber());

        this.saveUser(updatedAuthenticatedUser);
    }

    private String getAuthenticatedUserId() {
        return this.authenticatedUserService.getAuthenticatedUserId();
    }

    private User getUserById(String userId) {
        return this.userGateway.findById(userId)
                .orElseThrow(() -> UserNotFoundException.with("User not found"));
    }

    private void saveUser(User user) {
        this.userGateway.save(user);
    }
}
