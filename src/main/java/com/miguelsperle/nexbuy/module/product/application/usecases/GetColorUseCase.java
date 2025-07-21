package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ColorNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetColorUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IColorGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

public class GetColorUseCase implements IGetColorUseCase {
    private final IColorGateway colorGateway;

    public GetColorUseCase(IColorGateway colorGateway) {
        this.colorGateway = colorGateway;
    }

    @Override
    public GetColorUseCaseOutput execute(GetColorUseCaseInput getColorUseCaseInput) {
        final Color color = this.getColorById(getColorUseCaseInput.colorId());

        return GetColorUseCaseOutput.from(color);
    }

    private Color getColorById(String colorId) {
        return this.colorGateway.findById(colorId)
                .orElseThrow(() -> new ColorNotFoundException("Color not found"));
    }
}
