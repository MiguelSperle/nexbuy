package com.miguelsperle.nexbuy.shared.domain.events;

public record UserCreatedEvent(
        String id
) {
    public static UserCreatedEvent from(String id) {
        return new UserCreatedEvent(id);
    }
}
