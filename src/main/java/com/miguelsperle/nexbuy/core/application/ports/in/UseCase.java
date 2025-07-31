package com.miguelsperle.nexbuy.core.application.ports.in;

public interface UseCase<IN, OUT> {
    OUT execute(IN input);
}
