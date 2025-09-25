package com.miguelsperle.nexbuy.module.user.utils;

import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public class RefreshTokenBuilderTest {
    public static RefreshToken create(String userId, LocalDateTime time) {
        return RefreshToken.with(
                IdentifierUtils.generateUUID(),
                userId,
                IdentifierUtils.generateUUID(),
                time,
                TimeUtils.now()
        );
    }
}
