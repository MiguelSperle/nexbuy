package com.miguelsperle.nexbuy.module.user.domain.events;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserVerificationCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserVerificationCodeCreatedEvent extends ApplicationEvent {
    private final UserVerificationCode userVerificationCode;

    public UserVerificationCodeCreatedEvent(Object source, UserVerificationCode userVerificationCode) {
        super(source);
        this.userVerificationCode = userVerificationCode;
    }
}
