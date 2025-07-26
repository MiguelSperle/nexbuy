package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.ColorAssociatedWithProductsException;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.ColorNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IDeleteColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

public class DeleteColorUseCase implements IDeleteColorUseCase {
    private final IColorRepository colorRepository;
    private final IProductRepository productRepository;

    public DeleteColorUseCase(IColorRepository colorRepository, IProductRepository productRepository) {
        this.colorRepository = colorRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void execute(DeleteColorUseCaseInput deleteColorUseCaseInput) {
        final Color color = this.getColorById(deleteColorUseCaseInput.colorId());

        if (this.verifyProductAlreadyExistsByColorId(color.getId())) {
            throw ColorAssociatedWithProductsException.with("Color cannot be deleted because it is already associated with products");
        }

        this.deleteColorById(color.getId());
    }

    private Color getColorById(String colorId) {
        return this.colorRepository.findById(colorId)
                .orElseThrow(() -> ColorNotFoundException.with("Color not found"));
    }

    private boolean verifyProductAlreadyExistsByColorId(String colorId) {
        return this.productRepository.existsByColorId(colorId);
    }

    private void deleteColorById(String colorId) {
        this.colorRepository.deleteById(colorId);
    }
}
