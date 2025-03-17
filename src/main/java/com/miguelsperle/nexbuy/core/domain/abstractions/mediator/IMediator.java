package com.miguelsperle.nexbuy.core.domain.abstractions.mediator;

public interface IMediator {
    <OUT> OUT send(IRequest<OUT> request);
}
