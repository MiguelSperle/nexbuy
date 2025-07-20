package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.core.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.core.domain.pagination.SearchQuery;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.GetProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetProductsUseCaseOutput;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IGetProductsUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IBrandGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.ICategoryGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IColorGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Brand;
import com.miguelsperle.nexbuy.module.product.domain.entities.Category;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.module.product.domain.entities.Product;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetProductsUseCase implements IGetProductsUseCase {
    private final IProductGateway productGateway;
    private final ICategoryGateway categoryGateway;
    private final IBrandGateway brandGateway;
    private final IColorGateway colorGateway;

    public GetProductsUseCase(
            IProductGateway productGateway,
            ICategoryGateway categoryGateway,
            IBrandGateway brandGateway,
            IColorGateway colorGateway
    ) {
        this.productGateway = productGateway;
        this.categoryGateway = categoryGateway;
        this.brandGateway = brandGateway;
        this.colorGateway = colorGateway;
    }

    @Override
    public GetProductsUseCaseOutput execute(GetProductsUseCaseInput getProductsUseCaseInput) {
        final Pagination<Product> paginatedProducts = this.getAllProductsPaginated(getProductsUseCaseInput.searchQuery());

        final List<String> categoriesIds = paginatedProducts.items().stream().map(Product::getCategoryId).toList();
        final List<String> brandsIds = paginatedProducts.items().stream().map(Product::getBrandId).toList();
        final List<String> colorsIds = paginatedProducts.items().stream().map(Product::getColorId).toList();

        final Map<String, Category> categoriesMap = this.getCategoriesByIds(categoriesIds)
                .stream().collect(Collectors.toMap(Category::getId, Function.identity()));

        final Map<String, Brand> brandsMap = this.getBrandsByIds(brandsIds)
                .stream().collect(Collectors.toMap(Brand::getId, Function.identity()));

        final Map<String, Color> colorsMap = this.getColorsByIds(colorsIds)
                .stream().collect(Collectors.toMap(Color::getId, Function.identity()));

        return GetProductsUseCaseOutput.from(
                paginatedProducts,
                categoriesMap,
                brandsMap,
                colorsMap
        );
    }

    private Pagination<Product> getAllProductsPaginated(SearchQuery searchQuery) {
        return this.productGateway.findAllPaginated(searchQuery);
    }

    private List<Category> getCategoriesByIds(List<String> categoriesIds) {
        return this.categoryGateway.findAllByIds(categoriesIds);
    }

    private List<Brand> getBrandsByIds(List<String> brandsIds) {
        return this.brandGateway.findAllByIds(brandsIds);
    }

    private List<Color> getColorsByIds(List<String> colorsIds) {
        return this.colorGateway.findAllByIds(colorsIds);
    }
}
