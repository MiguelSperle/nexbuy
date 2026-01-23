package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.UpdateColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.usecases.UpdateColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

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
                throw DomainException.with("Color with this name already exists", 409);
            }
        }

        final Color updatedColor = color.withName(updateColorUseCaseInput.name());

        this.saveColor(updatedColor);
    }

    private Color getColorById(String colorId) {
        return this.colorRepository.findById(colorId)
                .orElseThrow(() -> NotFoundException.with("Color not found"));
    }

    private boolean verifyColorAlreadyExistsByName(String name) {
        return this.colorRepository.existsByName(name);
    }

    private void saveColor(Color color) {
        this.colorRepository.save(color);
    }
}
