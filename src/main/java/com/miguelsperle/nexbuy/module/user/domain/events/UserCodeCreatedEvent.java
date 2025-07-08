package com.miguelsperle.nexbuy.module.user.domain.events;

import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;

public record UserCodeCreatedEvent(
        String email,
        String code,
        CodeType codeType
) {
}
