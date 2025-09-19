package com.miguelsperle.nexbuy.module.user.utils.mocks;

import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;

public class RefreshTokenMock {
    public static RefreshToken create(String userId) {
        return RefreshToken.newRefreshToken(userId);
    }
}
