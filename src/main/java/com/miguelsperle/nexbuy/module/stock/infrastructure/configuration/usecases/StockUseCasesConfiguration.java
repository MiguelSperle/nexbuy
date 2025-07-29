package com.miguelsperle.nexbuy.module.stock.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.stock.application.ports.in.ICreateStockUseCase;
import com.miguelsperle.nexbuy.module.stock.application.ports.out.persistence.IStockRepository;
import com.miguelsperle.nexbuy.module.stock.application.usecases.CreateStockUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockUseCasesConfiguration {
    @Bean
    public ICreateStockUseCase createStockUseCase(IStockRepository stockRepository) {
        return new CreateStockUseCase(stockRepository);
    }
}
