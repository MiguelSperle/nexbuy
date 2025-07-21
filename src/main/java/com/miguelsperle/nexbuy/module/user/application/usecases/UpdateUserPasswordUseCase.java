package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.ISecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserPasswordUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.InvalidCurrentPasswordException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IUpdateUserPasswordUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public class UpdateUserPasswordUseCase implements IUpdateUserPasswordUseCase {
    private final ISecurityContextService authenticatedUserService;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final IUserGateway userGateway;

    public UpdateUserPasswordUseCase(
            ISecurityContextService authenticatedUserService,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            IUserGateway userGateway
    ) {
        this.authenticatedUserService = authenticatedUserService;
        this.passwordEncryptorProvider = passwordEncryptorProvider;
        this.userGateway = userGateway;
    }

    @Override
    public void execute(UpdateUserPasswordUseCaseInput updateUserPasswordUseCaseInput) {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final User user = this.getUserById(authenticatedUserId);

        if (!this.validatePassword(updateUserPasswordUseCaseInput.currentPassword(), user.getPassword())) {
            throw InvalidCurrentPasswordException.with("Invalid current password");
        }

        final String encodedPassword = this.passwordEncryptorProvider.encode(updateUserPasswordUseCaseInput.password());

        final User updatedAuthenticatedUser = user.withPassword(encodedPassword);

        this.saveUser(updatedAuthenticatedUser);
    }

    private boolean validatePassword(String password, String encodedPassword) {
        return this.passwordEncryptorProvider.matches(password, encodedPassword);
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
