package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.GetColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class GetColorUseCaseImpl implements GetColorUseCase {
    private final ColorRepository colorRepository;

    public GetColorUseCaseImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public GetColorUseCaseOutput execute(GetColorUseCaseInput getColorUseCaseInput) {
        final Color color = this.getColorById(getColorUseCaseInput.colorId());

        return GetColorUseCaseOutput.from(color);
    }

    private Color getColorById(String colorId) {
        return this.colorRepository.findById(colorId)
                .orElseThrow(() -> NotFoundException.with("Color not found"));
    }
}
