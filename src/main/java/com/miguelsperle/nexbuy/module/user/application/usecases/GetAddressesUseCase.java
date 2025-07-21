package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.exceptions.AuthenticatedUserIdNotFoundException;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressesUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IGetAddressesUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

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
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final List<Address> addresses = this.getAddressesByUserId(authenticatedUserId);

        return GetAddressesUseCaseOutput.from(addresses);
    }

    private String getAuthenticatedUserId() {
        return this.authenticatedUserService.getAuthenticatedUserId()
                .orElseThrow(() -> new AuthenticatedUserIdNotFoundException("Authenticated user id not found in security context"));
    }

    private List<Address> getAddressesByUserId(String userId) {
        return this.addressGateway.findAllByUserId(userId);
    }
}