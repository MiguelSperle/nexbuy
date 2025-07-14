package com.miguelsperle.nexbuy.module.product.application.dtos.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

import java.util.List;

public record GetColorsUseCaseOutput(
        List<Color> colors
) {
}
