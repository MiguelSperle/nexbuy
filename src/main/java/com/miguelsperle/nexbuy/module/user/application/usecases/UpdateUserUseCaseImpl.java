package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.UpdateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final SecurityContextService securityContextService;
    private final UserRepository userRepository;

    public UpdateUserUseCaseImpl(
            SecurityContextService securityContextService,
            UserRepository userRepository
    ) {
        this.securityContextService = securityContextService;
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
        return this.securityContextService.getAuthenticatedUserId();
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private void saveUser(User user) {
        this.userRepository.save(user);
    }
}
