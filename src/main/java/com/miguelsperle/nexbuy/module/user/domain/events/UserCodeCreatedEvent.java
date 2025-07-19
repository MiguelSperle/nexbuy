package com.miguelsperle.nexbuy.module.user.domain.events;

import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;

public record UserCodeCreatedEvent(
        String email,
        String code,
        CodeType codeType
) {
    public static UserCodeCreatedEvent from(String email, String code, CodeType codeType) {
        return new UserCodeCreatedEvent(email, code, codeType);
    }
}
