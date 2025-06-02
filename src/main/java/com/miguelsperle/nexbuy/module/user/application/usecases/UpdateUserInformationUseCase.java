package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.module.user.application.dtos.UpdateUserInformationUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IUpdateUserInformationUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateUserInformationUseCase implements IUpdateUserInformationUseCase {
    private final IAuthenticatedUserService authenticatedUserService;
    private final IUserGateway userGateway;

    @Override
    public void execute(UpdateUserInformationUseCaseInput updateUserInformationUseCaseInput) {
        final User user = this.authenticatedUserService.getAuthenticatedUser();

        final User userUpdated = user.withFirstName(updateUserInformationUseCaseInput.getFirstName())
                .withLastName(updateUserInformationUseCaseInput.getLastName())
                .withPhoneNumber(updateUserInformationUseCaseInput.getPhoneNumber());

        this.userGateway.save(userUpdated);
    }
}
