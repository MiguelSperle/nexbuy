package com.miguelsperle.nexbuy.module.user.utils.mocks;

import com.miguelsperle.nexbuy.module.user.domain.entities.Address;

public class AddressMock {
    public static Address createAddress(String userId) {
        return Address.newAddress(
                userId,
                "Test Avenue",
                "test123",
                "00000-000",
                "Testing North",
                "Test City",
                "FL",
                "Near of Main Avenue"
        );
    }
}
