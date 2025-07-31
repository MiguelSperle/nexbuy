package com.miguelsperle.nexbuy.core.application.ports.in;

public interface UseCaseWithoutReturn<IN> {
    void execute(IN input);
}
