package com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common;

public interface UseCase<IN, OUT> {
    OUT execute(IN input);
}
