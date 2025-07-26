package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.ColorAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.ports.in.IRegisterColorUseCase;
import com.miguelsperle.nexbuy.module.product.application.ports.out.persistence.IColorRepository;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

public class RegisterColorUseCase implements IRegisterColorUseCase {
    private final IColorRepository colorRepository;

    public RegisterColorUseCase(IColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public void execute(RegisterColorUseCaseInput registerColorUseCaseInput) {
        if (this.verifyColorAlreadyExistsByName(registerColorUseCaseInput.name())) {
            throw ColorAlreadyExistsException.with("Color with this name already exists");
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
