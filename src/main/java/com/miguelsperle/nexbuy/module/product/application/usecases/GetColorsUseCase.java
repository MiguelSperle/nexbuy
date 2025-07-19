package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetColorsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetColorsUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IColorGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

import java.util.List;

public class GetColorsUseCase implements IGetColorsUseCase {
    private final IColorGateway colorGateway;

    public GetColorsUseCase(IColorGateway colorGateway) {
        this.colorGateway = colorGateway;
    }

    @Override
    public GetColorsUseCaseOutput execute() {
        final List<Color> colors = this.getAllColors();

        return GetColorsUseCaseOutput.from(colors);
    }

    private List<Color> getAllColors() {
        return this.colorGateway.findAll();
    }
}
