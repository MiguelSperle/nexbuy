package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.ColorAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.ColorNotFoundException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.UpdateColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

public class UpdateColorUseCaseImpl implements UpdateColorUseCase {
    private final ColorRepository colorRepository;

    public UpdateColorUseCaseImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public void execute(UpdateColorUseCaseInput updateColorUseCaseInput) {
        final Color color = this.getColorById(updateColorUseCaseInput.colorId());

        if (!updateColorUseCaseInput.name().equalsIgnoreCase(color.getName())) {
            if (this.verifyColorAlreadyExistsByName(updateColorUseCaseInput.name())) {
                throw ColorAlreadyExistsException.with("Color with this name already exists");
            }
        }

        final Color updatedColor = color.withName(updateColorUseCaseInput.name());

        this.saveColor(updatedColor);
    }

    private Color getColorById(String colorId) {
        return this.colorRepository.findById(colorId)
                .orElseThrow(() -> ColorNotFoundException.with("Color not found"));
    }

    private boolean verifyColorAlreadyExistsByName(String name) {
        return this.colorRepository.existsByName(name);
    }

    private void saveColor(Color color) {
        this.colorRepository.save(color);
    }
}
