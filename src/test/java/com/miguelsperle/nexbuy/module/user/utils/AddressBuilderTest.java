package com.miguelsperle.nexbuy.module.user.utils;

import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class AddressBuilderTest {
    public static Address create(String userId) {
        return Address.with(
                IdentifierUtils.generateUUID(),
                userId,
                "Wayne Avenue",
                "150A",
                "21000-200",
                "Wayne Island",
                "Gotham",
                "SP",
                "Near of Main Avenue",
                TimeUtils.now()
        );
    }
}
