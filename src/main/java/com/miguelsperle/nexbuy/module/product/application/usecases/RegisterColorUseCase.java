package com.miguelsperle.nexbuy.module.product.application.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.RegisterColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.exceptions.ColorAlreadyExistsException;
import com.miguelsperle.nexbuy.module.product.application.usecases.abstractions.IRegisterColorUseCase;
import com.miguelsperle.nexbuy.module.product.domain.abstractions.gateways.IColorGateway;
import com.miguelsperle.nexbuy.module.product.domain.entities.Color;

public class RegisterColorUseCase implements IRegisterColorUseCase {
    private final IColorGateway colorGateway;

    public RegisterColorUseCase(IColorGateway colorGateway) {
        this.colorGateway = colorGateway;
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
        return this.colorGateway.existsByName(name);
    }

    private void saveColor(Color color) {
        this.colorGateway.save(color);
    }
}
