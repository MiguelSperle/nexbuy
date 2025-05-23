package com.miguelsperle.nexbuy.core.domain.abstractions.providers;

public interface IPasswordEncryptorProvider {
    String encode(String password);
    boolean matches(String rawPassword, String encodedPassword);
}
