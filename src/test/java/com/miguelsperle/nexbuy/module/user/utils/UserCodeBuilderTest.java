package com.miguelsperle.nexbuy.module.user.utils;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public class UserCodeBuilderTest {
    public static UserCode create(String userId, UserCodeType userCodeType, LocalDateTime time) {
        return UserCode.with(
                IdentifierUtils.generateUUID(),
                userId,
                "WAYNE123",
                userCodeType,
                time,
                TimeUtils.now()
        );
    }
}
