package com.miguelsperle.nexbuy.module.user.utils.mocks;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;

public class UserCodeMock {
    public static UserCode create(String userId, UserCodeType userCodeType) {
        return UserCode.newUserCode(
                userId,
                "test code",
                userCodeType
        );
    }
}
