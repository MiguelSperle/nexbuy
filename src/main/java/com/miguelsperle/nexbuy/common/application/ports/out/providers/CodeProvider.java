package com.miguelsperle.nexbuy.common.application.ports.out.providers;

public interface CodeProvider {
    String generateCode(int codeLength, String characters);
}
