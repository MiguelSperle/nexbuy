package com.miguelsperle.nexbuy.module.product.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetColorsUseCaseOutput;

import java.util.List;

public record GetColorsResponse(
        String id,
        String name
) {
    public static List<GetColorsResponse> fromOutput(GetColorsUseCaseOutput getColorsUseCaseOutput) {
        return getColorsUseCaseOutput.colors().stream().map(color -> new GetColorsResponse(
                color.getId(),
                color.getName()
        )).toList();
    }
}
