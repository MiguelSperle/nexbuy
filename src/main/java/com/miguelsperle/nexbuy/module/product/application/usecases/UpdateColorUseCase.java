package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ColorAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ColorNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IUpdateColorUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IColorGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

public class UpdateColorUseCase implements IUpdateColorUseCase {
    private final IColorGateway colorGateway;

    public UpdateColorUseCase(IColorGateway colorGateway) {
        this.colorGateway = colorGateway;
    }

    @Override
    public void execute(UpdateColorUseCaseInput updateColorUseCaseInput) {
        final Color color = this.getColorById(updateColorUseCaseInput.colorId());

        if (!updateColorUseCaseInput.name().equalsIgnoreCase(color.getName())) {
            if (this.verifyColorAlreadyExistsByName(updateColorUseCaseInput.name())) {
                throw new ColorAlreadyExistsException("Color with this name already exists");
            }
        }

        final Color updatedColor = color.withName(updateColorUseCaseInput.name());

        this.saveColor(updatedColor);
    }

    private Color getColorById(String colorId) {
        return this.colorGateway.findById(colorId)
                .orElseThrow(() -> new ColorNotFoundException("Color not found"));
    }

    private boolean verifyColorAlreadyExistsByName(String name) {
        return this.colorGateway.existsByName(name);
    }

    private void saveColor(Color color) {
        this.colorGateway.save(color);
    }
}
