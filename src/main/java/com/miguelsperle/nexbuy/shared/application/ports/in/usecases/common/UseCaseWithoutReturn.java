package com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common;

public interface UseCaseWithoutReturn<IN> {
    void execute(IN input);
}
