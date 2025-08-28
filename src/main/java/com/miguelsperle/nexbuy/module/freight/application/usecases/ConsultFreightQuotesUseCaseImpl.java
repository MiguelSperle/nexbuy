package com.miguelsperle.nexbuy.module.freight.application.usecases;

import com.miguelsperle.nexbuy.module.freight.application.ports.in.usecases.ConsultFreightQuotesUseCase;
import com.miguelsperle.nexbuy.module.freight.application.ports.out.services.FreightService;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.ConsultFreightQuotesUseCaseInput;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.complement.ProductsComplementInput;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.outputs.ConsultFreightQuotesUseCaseOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultFreightQuotesUseCaseImpl implements ConsultFreightQuotesUseCase {
    private final FreightService freightService;

    public ConsultFreightQuotesUseCaseImpl(FreightService freightService) {
        this.freightService = freightService;
    }

    @Override
    public ConsultFreightQuotesUseCaseOutput execute(ConsultFreightQuotesUseCaseInput consultFreightQuotesUseCaseInput) {
        final Map<String, Object> toMap = this.createToMap(consultFreightQuotesUseCaseInput.toCep());

        final List<Map<String, Object>> productMapList = this.createProductMapList(consultFreightQuotesUseCaseInput.productsComplementInput());

        final String response = this.freightService.consult(toMap, productMapList);

        return ConsultFreightQuotesUseCaseOutput.from(response);
    }

    private Map<String, Object> createToMap(String toCep) {
        final Map<String, Object> toMap = new HashMap<>();
        toMap.put("postal_code", toCep);
        return toMap;
    }

    private List<Map<String, Object>> createProductMapList(List<ProductsComplementInput> products) {
        final List<Map<String, Object>> productMapList = new ArrayList<>();

        for (ProductsComplementInput product : products) {
            final Map<String, Object> productMap = new HashMap<>();
            productMap.put("id", product.id());
            productMap.put("height", product.height() / 10.0);
            productMap.put("width", product.width() / 10.0);
            productMap.put("length", product.length() / 10.0);
            productMap.put("weight", product.weight() / 1000.0);
            productMap.put("quantity", product.quantity());
            productMapList.add(productMap);
        }

        return productMapList;
    }
}
