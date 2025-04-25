package com.miguelsperle.nexbuy.core.domain.abstractions.providers;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public interface IJwtTokenProvider {
    String generateJwt(User user);
}
