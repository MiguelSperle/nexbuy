package com.miguelsperle.nexbuy.core.domain.abstractions.security;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;

import java.util.Optional;

public interface IAuthenticatedUserService {
    Optional<User> getAuthenticatedUser();
}
