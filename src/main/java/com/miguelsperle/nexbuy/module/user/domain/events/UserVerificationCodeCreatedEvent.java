package com.miguelsperle.nexbuy.module.user.domain.events;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserVerificationCode;
import lombok.Getter;

@Getter
public class UserVerificationCodeCreatedEvent {
    private final UserVerificationCode userVerificationCode;

    public UserVerificationCodeCreatedEvent(UserVerificationCode userVerificationCode) {
        this.userVerificationCode = userVerificationCode;
    }
}
