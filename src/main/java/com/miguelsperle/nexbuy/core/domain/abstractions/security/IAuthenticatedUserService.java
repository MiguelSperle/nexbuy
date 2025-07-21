package com.miguelsperle.nexbuy.core.domain.abstractions.security;

import java.util.Optional;

public interface IAuthenticatedUserService {
    Optional<String> getAuthenticatedUserId();
}
