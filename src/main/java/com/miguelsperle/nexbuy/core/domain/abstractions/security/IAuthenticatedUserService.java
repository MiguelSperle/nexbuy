package com.miguelsperle.nexbuy.core.domain.abstractions.security;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public interface IAuthenticatedUserService {
    User getAuthenticatedUser();
}
