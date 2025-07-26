package com.miguelsperle.nexbuy.core.application.ports.in;

public interface IUseCaseWithoutArgument<OUT> {
    OUT execute();
}
