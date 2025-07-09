package com.miguelsperle.nexbuy.module.product.application.dtos.outputs;

import com.miguelsperle.nexbuy.module.product.domain.entities.Category;

import java.util.List;

public record GetCategoriesUseCaseOutput(
        List<Category> categories
) {
}
