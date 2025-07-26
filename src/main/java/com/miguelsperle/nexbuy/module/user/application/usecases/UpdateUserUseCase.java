package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.security.ISecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IUpdateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public class UpdateUserUseCase implements IUpdateUserUseCase {
    private final ISecurityContextService securityContextService;
    private final IUserRepository userRepository;

    public UpdateUserUseCase(
            ISecurityContextService securityContextService,
            IUserRepository userRepository
    ) {
        this.securityContextService = securityContextService;
        this.userRepository = userRepository;
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
        return this.securityContextService.getAuthenticatedUserId();
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.with("User not found"));
    }

    private void saveUser(User user) {
        this.userRepository.save(user);
    }
}
