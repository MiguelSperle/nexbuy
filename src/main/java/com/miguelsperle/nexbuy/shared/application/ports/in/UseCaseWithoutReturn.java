package com.miguelsperle.nexbuy.shared.application.ports.in;

public interface UseCaseWithoutReturn<IN> {
    void execute(IN input);
}
