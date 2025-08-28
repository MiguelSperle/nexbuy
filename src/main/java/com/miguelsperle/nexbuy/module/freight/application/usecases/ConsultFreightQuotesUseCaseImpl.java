package com.miguelsperle.nexbuy.module.shipping.application.usecases;

import com.miguelsperle.nexbuy.module.shipping.application.ports.in.usecases.ConsultFreightQuotesUseCase;
import com.miguelsperle.nexbuy.module.shipping.application.ports.out.services.FreightService;
import com.miguelsperle.nexbuy.module.shipping.application.usecases.io.inputs.ConsultFreightQuotesUseCaseInput;
import com.miguelsperle.nexbuy.module.shipping.application.usecases.io.inputs.complement.ProductsComplementInput;
import com.miguelsperle.nexbuy.module.shipping.application.usecases.io.outputs.ConsultFreightQuotesUseCaseOutput;

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
        final Map<String, Object> toMap = new HashMap<>();
        toMap.put("postal_code", consultFreightQuotesUseCaseInput.toCep());

        final List<Map<String, Object>> productMapList = this.getProductMapList(consultFreightQuotesUseCaseInput.productsComplementInput());

        final String response = this.freightService.consult(toMap, productMapList);

        return ConsultFreightQuotesUseCaseOutput.from(response);
    }

    private List<Map<String, Object>> getProductMapList(List<ProductsComplementInput> products) {
        final List<Map<String, Object>> productMapList = new ArrayList<>();

        for (ProductsComplementInput p : products) {
            final Map<String, Object> productMap = new HashMap<>();
            productMap.put("id", p.id());
            productMap.put("height", p.height() / 10.0);
            productMap.put("width", p.width() / 10.0);
            productMap.put("length", p.length() / 10.0);
            productMap.put("weight", p.weight() / 1000.0);
            productMap.put("quantity", p.quantity());
            productMapList.add(productMap);
        }

        return productMapList;
    }
}
