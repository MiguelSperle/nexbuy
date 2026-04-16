package com.miguelsperle.nexbuy.shared.application.abstractions.providers;

public interface PasswordEncryptorProvider {
    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
