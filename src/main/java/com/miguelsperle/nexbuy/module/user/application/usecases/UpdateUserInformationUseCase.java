package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.UpdateUserInformationUseCaseInput;
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
        final User authenticatedUser = this.authenticatedUserService.getAuthenticatedUser();

        final User authenticatedUserUpdated = authenticatedUser.withFirstName(updateUserInformationUseCaseInput.getFirstName())
                .withLastName(updateUserInformationUseCaseInput.getLastName())
                .withPhoneNumber(updateUserInformationUseCaseInput.getPhoneNumber());

        this.userGateway.save(authenticatedUserUpdated);
    }
}
