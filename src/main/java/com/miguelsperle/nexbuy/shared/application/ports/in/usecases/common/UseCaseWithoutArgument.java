package com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common;

public interface UseCaseWithoutArgument<OUT> {
    OUT execute();
}
