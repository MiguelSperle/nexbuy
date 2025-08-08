package com.miguelsperle.nexbuy.shared.application.ports.in;

public interface UseCase<IN, OUT> {
    OUT execute(IN input);
}
