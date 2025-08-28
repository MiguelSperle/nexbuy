package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.out.services;

import com.miguelsperle.nexbuy.module.freight.application.ports.out.services.FreightService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.http.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FreightServiceImpl implements FreightService {
    private final WebClient webClient;

    @Value("${spring.freight-api.fromCep}")
    private String fromCep;

    @Value("${spring.freight-api.url}")
    private String url;

    @Value("${spring.freight-api.token}")
    private String token;

    @Value("${spring.freight-api.email}")
    private String email;

    @Override
    public String consult(Map<String, Object> toMap, List<Map<String, Object>> productMapList) {
        final Map<String, Object> fromMap = this.createFromMap();

        final Map<String, Object> requestBodyMap = this.createRequestBodyMap(fromMap, toMap, productMapList);

        return this.webClient.post()
                .uri(this.url)
                .headers(this::setHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyMap)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private Map<String, Object> createFromMap() {
        final Map<String, Object> fromMap = new HashMap<>();
        fromMap.put("postal_code", this.fromCep);
        return fromMap;
    }

    private Map<String, Object> createRequestBodyMap(Map<String, Object> fromMap, Map<String, Object> toMap, List<Map<String, Object>> productMapList) {
        final Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("from", fromMap);
        requestBodyMap.put("to", toMap);
        requestBodyMap.put("products", productMapList);
        return requestBodyMap;
    }

    private void setHeaders(HttpHeaders headers) {
        headers.setBearerAuth(this.token);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("User-Agent", "Application " + this.email);
    }
}
