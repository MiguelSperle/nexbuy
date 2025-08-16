package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.DeleteColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ProductRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class DeleteColorUseCaseImpl implements DeleteColorUseCase {
    private final ColorRepository colorRepository;
    private final ProductRepository productRepository;

    public DeleteColorUseCaseImpl(ColorRepository colorRepository, ProductRepository productRepository) {
        this.colorRepository = colorRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void execute(DeleteColorUseCaseInput deleteColorUseCaseInput) {
        final Color color = this.getColorById(deleteColorUseCaseInput.colorId());

        if (this.verifyProductAlreadyExistsByColorId(color.getId())) {
            throw DomainException.with("Color cannot be deleted because it is already associated with products", 409);
        }

        this.deleteColorById(color.getId());
    }

    private Color getColorById(String colorId) {
        return this.colorRepository.findById(colorId)
                .orElseThrow(() -> NotFoundException.with("Color not found"));
    }

    private boolean verifyProductAlreadyExistsByColorId(String colorId) {
        return this.productRepository.existsByColorId(colorId);
    }

    private void deleteColorById(String colorId) {
        this.colorRepository.deleteById(colorId);
    }
}
