package com.miguelsperle.nexbuy.module.user.utils;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class UserCodeBuilderTest {
    public static UserCode create(String userId, UserCodeType userCodeType) {
        return UserCode.with(
                IdentifierUtils.generateUUID(),
                userId,
                "WAYNE123",
                userCodeType,
                TimeUtils.now().plusMinutes(15),
                TimeUtils.now()
        );
    }
}
