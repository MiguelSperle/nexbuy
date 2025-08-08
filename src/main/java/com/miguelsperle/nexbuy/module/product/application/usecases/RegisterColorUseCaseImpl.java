package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.ports.in.RegisterColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.ColorRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;

public class RegisterColorUseCaseImpl implements RegisterColorUseCase {
    private final ColorRepository colorRepository;

    public RegisterColorUseCaseImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public void execute(RegisterColorUseCaseInput registerColorUseCaseInput) {
        if (this.verifyColorAlreadyExistsByName(registerColorUseCaseInput.name())) {
            throw DomainException.with("Color with this name already exists", 409);
        }

        final Color newColor = Color.newColor(registerColorUseCaseInput.name());

        this.saveColor(newColor);
    }

    private boolean verifyColorAlreadyExistsByName(String name) {
        return this.colorRepository.existsByName(name);
    }

    private void saveColor(Color color) {
        this.colorRepository.save(color);
    }
}
