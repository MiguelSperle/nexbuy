package com.miguelsperle.nexbuy.common.application.ports.in.usecases.common;

public interface UseCaseWithoutReturn<IN> {
    void execute(IN input);
}
