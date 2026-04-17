package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.abstractions.usecases.UpdateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final SecurityService securityService;
    private final UserRepository userRepository;

    public UpdateUserUseCaseImpl(
            SecurityService securityService,
            UserRepository userRepository
    ) {
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(UpdateUserUseCaseInput updateUserUseCaseInput) {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final User user = this.getUserById(authenticatedUserId);

        final User updatedUser = user.withFirstName(updateUserUseCaseInput.firstName())
                .withLastName(updateUserUseCaseInput.lastName())
                .withPhoneNumber(updateUserUseCaseInput.phoneNumber());

        this.saveUser(updatedUser);
    }

    private String getAuthenticatedUserId() {
        return this.securityService.getUserId();
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private void saveUser(User user) {
        this.userRepository.save(user);
    }
}
