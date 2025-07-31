package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetColorsUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

import java.util.List;

public class GetColorsUseCaseImpl implements GetColorsUseCase {
    private final ColorRepository colorRepository;

    public GetColorsUseCaseImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public GetColorsUseCaseOutput execute() {
        final List<Color> colors = this.getAllColors();

        return GetColorsUseCaseOutput.from(colors);
    }

    private List<Color> getAllColors() {
        return this.colorRepository.findAll();
    }
}
