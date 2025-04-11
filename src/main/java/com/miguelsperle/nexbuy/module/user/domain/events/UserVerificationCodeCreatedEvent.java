package com.miguelsperle.nexbuy.module.user.domain.events;

import lombok.Getter;

@Getter
public class UserVerificationCodeCreatedEvent {
    private final String email;
    private final String code;

    public UserVerificationCodeCreatedEvent(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
