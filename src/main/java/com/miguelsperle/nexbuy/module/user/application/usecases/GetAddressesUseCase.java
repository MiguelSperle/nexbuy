package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.ISecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressesUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IGetAddressesUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IAddressGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

import java.util.List;

public class GetAddressesUseCase implements IGetAddressesUseCase {
    private final IAddressGateway addressGateway;
    private final ISecurityContextService securityContextService;
    private final IUserGateway userGateway;

    public GetAddressesUseCase(
            IAddressGateway addressGateway,
            ISecurityContextService securityContextService,
            IUserGateway userGateway
    ) {
        this.addressGateway = addressGateway;
        this.securityContextService = securityContextService;
        this.userGateway = userGateway;
    }

    @Override
    public GetAddressesUseCaseOutput execute() {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final User user = this.getUserById(authenticatedUserId);

        final List<Address> addresses = this.getAddressesByUserId(user.getId());

        return GetAddressesUseCaseOutput.from(addresses);
    }

    private String getAuthenticatedUserId() {
        return this.securityContextService.getAuthenticatedUserId();
    }

    private User getUserById(String userId) {
        return this.userGateway.findById(userId).orElseThrow(() -> UserNotFoundException.with("User not found"));
    }

    private List<Address> getAddressesByUserId(String userId) {
        return this.addressGateway.findAllByUserId(userId);
    }
}