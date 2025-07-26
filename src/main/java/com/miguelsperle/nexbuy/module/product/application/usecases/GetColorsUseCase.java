package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IGetColorsUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IColorRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

import java.util.List;

public class GetColorsUseCase implements IGetColorsUseCase {
    private final IColorRepository colorRepository;

    public GetColorsUseCase(IColorRepository colorRepository) {
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
