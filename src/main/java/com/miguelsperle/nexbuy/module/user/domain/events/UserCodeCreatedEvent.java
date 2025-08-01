package com.miguelsperle.nexbuy.module.user.domain.events;

import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;

public record UserCodeCreatedEvent(
        String code,
        CodeType codeType,
        String userId
) {
    public static UserCodeCreatedEvent from(
            String code,
            CodeType codeType,
            String userId
    ) {
        return new UserCodeCreatedEvent(
                code,
                codeType,
                userId
        );
    }
}
