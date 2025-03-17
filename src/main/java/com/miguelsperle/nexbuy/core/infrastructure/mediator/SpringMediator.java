package com.miguelsperle.nexbuy.core.infrastructure.mediator;

import com.miguelsperle.nexbuy.core.domain.abstractions.mediator.IMediator;
import com.miguelsperle.nexbuy.core.domain.abstractions.mediator.IRequest;
import com.miguelsperle.nexbuy.core.domain.abstractions.mediator.IRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

@Component
@RequiredArgsConstructor
public class SpringMediator implements IMediator {
    private final AbstractApplicationContext abstractApplicationContext;

    @Override
    @SuppressWarnings("unchecked")
    public <OUT> OUT send(IRequest<OUT> request) {
        if (request == null) {
            throw new NullPointerException("The given request object cannot be null");
        }

        final Class<?> requestType = request.getClass();
        final Class<OUT> responseType = (Class<OUT>) ((ParameterizedType) requestType.getGenericInterfaces()[0]).getActualTypeArguments()[0];

        final String[] beanNames = this.abstractApplicationContext.getBeanNamesForType(ResolvableType.forClassWithGenerics(IRequestHandler.class, requestType, responseType));

        if (beanNames.length == 0) {
            throw new IllegalStateException("No handlers seemed to be registered to handle the given request. Make sure the handler is defined and marked as a spring component");
        }

        if (beanNames.length > 1) {
            throw new IllegalStateException("More than one handlers found. Only one handler per request is allowed.");
        }

        final IRequestHandler<IRequest<OUT>, OUT> requestHandler = (IRequestHandler<IRequest<OUT>, OUT>) this.abstractApplicationContext.getBean(beanNames[0]);

        return requestHandler.handle(request);
    }
}
