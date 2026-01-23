package com.miguelsperle.nexbuy.module.product.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorsUseCaseOutput;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;

public record GetColorsResponse(
        String id,
        String name
) {
    public static Pagination<GetColorsResponse> from(GetColorsUseCaseOutput getColorsUseCaseOutput) {
        return getColorsUseCaseOutput.paginatedColors().map(paginatedColor -> new GetColorsResponse(
                paginatedColor.getId(),
                paginatedColor.getName()
        ));
    }
}
