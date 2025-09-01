package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.web.dtos.responses;

import com.fasterxml.jackson.databind.JsonNode;
import com.miguelsperle.nexbuy.module.freight.application.usecases.io.outputs.ConsultFreightQuotesUseCaseOutput;
import com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.web.dtos.responses.complement.CompanyComplementResponse;
import com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.web.dtos.responses.complement.DeliveryComplementResponse;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import com.miguelsperle.nexbuy.shared.infrastructure.utils.JsonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record ConsultFreightQuotesResponse(
        String id,
        String name,
        CompanyComplementResponse company,
        BigDecimal price,
        DeliveryComplementResponse delivery
) {
    public static List<ConsultFreightQuotesResponse> from(ConsultFreightQuotesUseCaseOutput consultFreightQuotesUseCaseOutput) {
        final List<ConsultFreightQuotesResponse> result = new ArrayList<>();

        final JsonNode root = JsonUtils.readTree(consultFreightQuotesUseCaseOutput.response(), JsonNode.class);

        for (JsonNode node : root) {
            if (!node.has("error")) {
                final String id = node.path("id").asText();
                final String name = node.path("name").asText();
                final String companyName = node.path("company").path("name").asText();
                final BigDecimal price = DecimalUtils.valueOf(node.path("price").asText());
                final int estimatedTime = node.path("delivery_time").asInt();
                final int minTime = node.path("delivery_range").path("min").asInt();
                final int maxTime = node.path("delivery_range").path("max").asInt();

                final CompanyComplementResponse companyComplementResponse = CompanyComplementResponse.from(companyName);
                final DeliveryComplementResponse deliveryComplementResponse = DeliveryComplementResponse.from(
                        estimatedTime, minTime, maxTime
                );
                final ConsultFreightQuotesResponse consultFreightQuotesResponse = ConsultFreightQuotesResponse.from(
                        id,
                        name,
                        companyComplementResponse,
                        price,
                        deliveryComplementResponse
                );

                result.add(consultFreightQuotesResponse);
            }
        }

        return result;
    }

    private static ConsultFreightQuotesResponse from(
            String id,
            String name,
            CompanyComplementResponse company,
            BigDecimal price,
            DeliveryComplementResponse delivery
    ) {
        return new ConsultFreightQuotesResponse(
                id,
                name,
                company,
                price,
                delivery
        );
    }
}
