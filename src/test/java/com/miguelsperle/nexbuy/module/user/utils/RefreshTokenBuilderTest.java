package com.miguelsperle.nexbuy.module.user.utils;

import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class RefreshTokenBuilderTest {
    public static RefreshToken create(String userId) {
        return RefreshToken.with(
                IdentifierUtils.generateUUID(),
                userId,
                IdentifierUtils.generateUUID(),
                TimeUtils.now().plusDays(15),
                TimeUtils.now()
        );
    }
}
