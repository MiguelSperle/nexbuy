package com.miguelsperle.nexbuy.common.application.ports.in.usecases.common;

public interface UseCase<IN, OUT> {
    OUT execute(IN input);
}
