package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetColorsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.GetColorsUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;

public class GetColorsUseCaseImpl implements GetColorsUseCase {
    private final ColorRepository colorRepository;

    public GetColorsUseCaseImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public GetColorsUseCaseOutput execute(GetColorsUseCaseInput getColorsUseCaseInput) {
        final Pagination<Color> paginatedColors = this.getAllPaginatedColors(getColorsUseCaseInput.searchQuery());

        return GetColorsUseCaseOutput.from(paginatedColors);
    }

    private Pagination<Color> getAllPaginatedColors(SearchQuery searchQuery) {
        return this.colorRepository.findAllPaginated(searchQuery);
    }
}
