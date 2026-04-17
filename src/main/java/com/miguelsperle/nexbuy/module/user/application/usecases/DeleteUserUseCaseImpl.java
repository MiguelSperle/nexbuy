package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityService;
import com.miguelsperle.nexbuy.module.user.application.abstractions.usecases.DeleteUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    public DeleteUserUseCaseImpl(UserRepository userRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @Override
    public void execute() {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final User user = this.getUserById(authenticatedUserId);

        final User updatedUser = user.withUserStatus(UserStatus.DELETED);

        this.saveUser(updatedUser);
    }

    private String getAuthenticatedUserId() {
        return this.securityService.getUserId();
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private void saveUser(User user) {
        this.userRepository.save(user);
    }
}
