package com.miguelsperle.nexbuy.common.application.ports.out.providers;

public interface PasswordEncryptorProvider {
    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
