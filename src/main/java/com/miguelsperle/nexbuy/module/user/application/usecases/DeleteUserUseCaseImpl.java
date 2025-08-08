package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.security.SecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.ports.in.DeleteUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserRepository userRepository;
    private final SecurityContextService securityContextService;

    public DeleteUserUseCaseImpl(UserRepository userRepository, SecurityContextService securityContextService) {
        this.userRepository = userRepository;
        this.securityContextService = securityContextService;
    }

    @Override
    public void execute() {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final User user = this.getUserById(authenticatedUserId);

        final User updatedUser = user.withUserStatus(UserStatus.DELETED);

        this.saveUser(updatedUser);
    }

    private String getAuthenticatedUserId() {
        return this.securityContextService.getAuthenticatedUserId();
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.with("User not found"));
    }

    private void saveUser(User user) {
        this.userRepository.save(user);
    }
}
