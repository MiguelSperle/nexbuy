package com.miguelsperle.nexbuy.core.application.ports.out.providers;

public interface IPasswordEncryptorProvider {
    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
