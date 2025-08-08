package com.miguelsperle.nexbuy.module.user.domain.events;

import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;

public record UserCodeCreatedEvent(
        String code,
        UserCodeType userCodeType,
        String userId
) {
    public static UserCodeCreatedEvent from(
            String code,
            UserCodeType userCodeType,
            String userId
    ) {
        return new UserCodeCreatedEvent(
                code,
                userCodeType,
                userId
        );
    }
}
