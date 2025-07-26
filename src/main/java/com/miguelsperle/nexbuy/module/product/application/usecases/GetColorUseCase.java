package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.ColorNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IGetColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IColorRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

public class GetColorUseCase implements IGetColorUseCase {
    private final IColorRepository colorRepository;

    public GetColorUseCase(IColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public GetColorUseCaseOutput execute(GetColorUseCaseInput getColorUseCaseInput) {
        final Color color = this.getColorById(getColorUseCaseInput.colorId());

        return GetColorUseCaseOutput.from(color);
    }

    private Color getColorById(String colorId) {
        return this.colorRepository.findById(colorId)
                .orElseThrow(() -> ColorNotFoundException.with("Color not found"));
    }
}
