package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.security.ISecurityContextService;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressesUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IGetAddressesUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IAddressRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

import java.util.List;

public class GetAddressesUseCase implements IGetAddressesUseCase {
    private final IAddressRepository addressRepository;
    private final ISecurityContextService securityContextService;
    private final IUserRepository userRepository;

    public GetAddressesUseCase(
            IAddressRepository addressRepository,
            ISecurityContextService securityContextService,
            IUserRepository userRepository
    ) {
        this.addressRepository = addressRepository;
        this.securityContextService = securityContextService;
        this.userRepository = userRepository;
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
        return this.userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.with("User not found"));
    }

    private List<Address> getAddressesByUserId(String userId) {
        return this.addressRepository.findAllByUserId(userId);
    }
}