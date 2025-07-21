package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorsUseCaseOutput;

import java.util.List;

public record GetColorsResponse(
        String id,
        String name
) {
    public static List<GetColorsResponse> from(GetColorsUseCaseOutput getColorsUseCaseOutput) {
        return getColorsUseCaseOutput.colors().stream().map(color -> new GetColorsResponse(
                color.getId(),
                color.getName()
        )).toList();
    }
}
