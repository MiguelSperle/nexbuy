package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.PasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.security.SecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserPasswordUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.InvalidCurrentPasswordException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.UpdateUserPasswordUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public class UpdateUserPasswordUseCaseImpl implements UpdateUserPasswordUseCase {
    private final SecurityContextService securityContextService;
    private final PasswordEncryptorProvider passwordEncryptorProvider;
    private final UserRepository userRepository;

    public UpdateUserPasswordUseCaseImpl(
            SecurityContextService securityContextService,
            PasswordEncryptorProvider passwordEncryptorProvider,
            UserRepository userRepository
    ) {
        this.securityContextService = securityContextService;
        this.passwordEncryptorProvider = passwordEncryptorProvider;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(UpdateUserPasswordUseCaseInput updateUserPasswordUseCaseInput) {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final User user = this.getUserById(authenticatedUserId);

        if (!this.validatePassword(updateUserPasswordUseCaseInput.currentPassword(), user.getPassword())) {
            throw InvalidCurrentPasswordException.with("Invalid current password");
        }

        final String encodedPassword = this.passwordEncryptorProvider.encode(updateUserPasswordUseCaseInput.password());

        final User updatedUser = user.withPassword(encodedPassword);

        this.saveUser(updatedUser);
    }

    private boolean validatePassword(String password, String encodedPassword) {
        return this.passwordEncryptorProvider.matches(password, encodedPassword);
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
