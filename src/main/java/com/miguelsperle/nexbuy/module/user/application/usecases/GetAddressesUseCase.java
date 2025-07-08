package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.exceptions.AuthenticatedUserNotFoundException;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAddressesUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IGetAddressesUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

import java.util.List;

public class GetAddressesUseCase implements IGetAddressesUseCase {
    private final IAddressGateway addressGateway;
    private final IAuthenticatedUserService authenticatedUserService;

    public GetAddressesUseCase(IAddressGateway addressGateway, IAuthenticatedUserService authenticatedUserService) {
        this.addressGateway = addressGateway;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public GetAddressesUseCaseOutput execute() {
        final User authenticatedUser = this.getAuthenticatedUser();

        final List<Address> addresses = this.getAddressesByUserId(authenticatedUser.getId());

        return new GetAddressesUseCaseOutput(addresses);
    }

    private User getAuthenticatedUser() {
        return this.authenticatedUserService.getAuthenticatedUser()
                .orElseThrow(() -> new AuthenticatedUserNotFoundException("Authenticated user not found in security context"));
    }

    private List<Address> getAddressesByUserId(String userId) {
        return this.addressGateway.findAllByUserId(userId);
    }
}