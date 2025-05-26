package com.miguelsperle.nexbuy.module.user.domain.events;

import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import lombok.Getter;

@Getter
public class UserCodeCreatedEvent {
    private final String email;
    private final String code;
    private final CodeType codeType;

    public UserCodeCreatedEvent(String email, String code, CodeType codeType) {
        this.email = email;
        this.code = code;
        this.codeType = codeType;
    }
}
