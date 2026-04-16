package com.miguelsperle.nexbuy.shared.application.abstractions.providers;

public interface CodeProvider {
    String generateCode(int codeLength, String characters);
}
