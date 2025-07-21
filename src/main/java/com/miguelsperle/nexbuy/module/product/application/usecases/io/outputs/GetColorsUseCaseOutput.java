package com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

import java.util.List;

public record GetColorsUseCaseOutput(
        List<Color> colors
) {
    public static GetColorsUseCaseOutput from(List<Color> colors) {
        return new GetColorsUseCaseOutput(colors);
    }
}
