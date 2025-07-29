package com.miguelsperle.nexbuy.module.stock.application.usecases;

import com.miguelsperle.nexbuy.module.stock.application.ports.in.ICreateStockUseCase;
import com.miguelsperle.nexbuy.module.stock.application.ports.out.persistence.IStockRepository;
import com.miguelsperle.nexbuy.module.stock.application.usecases.io.inputs.CreateStockUseCaseInput;
import com.miguelsperle.nexbuy.module.stock.domain.entities.Stock;

public class CreateStockUseCase implements ICreateStockUseCase {
    private final IStockRepository stockRepository;

    public CreateStockUseCase(IStockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void execute(CreateStockUseCaseInput createStockUseCaseInput) {
        final Stock newStock = Stock.newStock(
                createStockUseCaseInput.productId(),
                createStockUseCaseInput.sku(),
                createStockUseCaseInput.quantity()
        );

        this.saveStock(newStock);
    }

    private void saveStock(Stock stock) {
        this.stockRepository.save(stock);
    }
}
