package com.miguelsperle.nexbuy.module.user.utils.mocks;

import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;

public class RefreshTokenMock {
    public static RefreshToken refreshToken() {
        return RefreshToken.newRefreshToken("test-user-id");
    }
}
