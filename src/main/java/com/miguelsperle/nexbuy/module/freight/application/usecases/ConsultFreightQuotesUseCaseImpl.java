package com.miguelsperle.nexbuy.module.freight.application.usecases;

import com.miguelsperle.nexbuy.module.freight.application.ports.in.usecases.ConsultFreightQuotesUseCase;
import com.miguelsperle.nexbuy.module.freight.application.ports.out.services.FreightService;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.ConsultFreightQuotesUseCaseInput;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.inputs.complement.ItemsComplementInput;
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

        final List<Map<String, Object>> itemMapList = this.createItemMapList(consultFreightQuotesUseCaseInput.itemsComplementInput());

        final String response = this.freightService.consult(toMap, itemMapList);

        return ConsultFreightQuotesUseCaseOutput.from(response);
    }

    private Map<String, Object> createToMap(String toCep) {
        final Map<String, Object> toMap = new HashMap<>();
        toMap.put("postal_code", toCep);
        return toMap;
    }

    private List<Map<String, Object>> createItemMapList(List<ItemsComplementInput> items) {
        final List<Map<String, Object>> itemMapList = new ArrayList<>();

        for (ItemsComplementInput item : items) {
            final Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("id", item.id());
            itemMap.put("height", item.height() / 10.0);
            itemMap.put("width", item.width() / 10.0);
            itemMap.put("length", item.length() / 10.0);
            itemMap.put("weight", item.weight() / 1000.0);
            itemMap.put("quantity", item.quantity());
            itemMapList.add(itemMap);
        }

        return itemMapList;
    }
}
