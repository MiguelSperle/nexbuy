package com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;

public record GetColorsUseCaseOutput(
        Pagination<Color> paginatedColors
) {
    public static GetColorsUseCaseOutput from(Pagination<Color> paginatedColors) {
        return new GetColorsUseCaseOutput(paginatedColors);
    }
}
