package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.DeleteColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ColorAssociatedWithProductsException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ColorNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IDeleteColorUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IColorGateway;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IProductGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

public class DeleteColorUseCase implements IDeleteColorUseCase {
    private final IColorGateway colorGateway;
    private final IProductGateway productGateway;

    public DeleteColorUseCase(IColorGateway colorGateway, IProductGateway productGateway) {
        this.colorGateway = colorGateway;
        this.productGateway = productGateway;
    }

    @Override
    public void execute(DeleteColorUseCaseInput deleteColorUseCaseInput) {
        final Color color = this.getColorById(deleteColorUseCaseInput.colorId());

        if (this.verifyProductAlreadyExistsByColorId(color.getId())) {
            throw new ColorAssociatedWithProductsException("Color cannot be deleted because it is already associated with products");
        }

        this.deleteColorById(color.getId());
    }

    private Color getColorById(String colorId) {
        return this.colorGateway.findById(colorId)
                .orElseThrow(() -> new ColorNotFoundException("Color not found"));
    }

    private boolean verifyProductAlreadyExistsByColorId(String colorId) {
        return this.productGateway.existsByColorId(colorId);
    }

    private void deleteColorById(String colorId) {
        this.colorGateway.deleteById(colorId);
    }
}
