package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressesUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.GetAddressesUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.AddressRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

import java.util.List;

public class GetAddressesUseCaseImpl implements GetAddressesUseCase {
    private final AddressRepository addressRepository;
    private final SecurityContextService securityContextService;
    private final UserRepository userRepository;

    public GetAddressesUseCaseImpl(
            AddressRepository addressRepository,
            SecurityContextService securityContextService,
            UserRepository userRepository
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
        return this.userRepository.findById(userId).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private List<Address> getAddressesByUserId(String userId) {
        return this.addressRepository.findAllByUserId(userId);
    }
}