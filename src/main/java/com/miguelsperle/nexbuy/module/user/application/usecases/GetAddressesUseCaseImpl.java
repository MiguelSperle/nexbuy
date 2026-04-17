package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressesUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.abstractions.usecases.GetAddressesUseCase;
import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.AddressRepository;
import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

import java.util.List;

public class GetAddressesUseCaseImpl implements GetAddressesUseCase {
    private final AddressRepository addressRepository;
    private final SecurityService securityService;
    private final UserRepository userRepository;

    public GetAddressesUseCaseImpl(
            AddressRepository addressRepository,
            SecurityService securityService,
            UserRepository userRepository
    ) {
        this.addressRepository = addressRepository;
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    @Override
    public GetAddressesUseCaseOutput execute() {
        final String authenticatedUserId = this.getAuthenticatedUserId();

        final User user = this.getUserById(authenticatedUserId);

        final List<Address> addresses = this.getAllAddressesByUserId(user.getId());

        return GetAddressesUseCaseOutput.from(addresses);
    }

    private String getAuthenticatedUserId() {
        return this.securityService.getUserId();
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private List<Address> getAllAddressesByUserId(String userId) {
        return this.addressRepository.findAllByUserId(userId);
    }
}