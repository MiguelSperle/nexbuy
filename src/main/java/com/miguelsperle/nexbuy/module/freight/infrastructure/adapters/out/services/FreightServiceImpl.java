package com.miguelsperle.nexbuy.module.shipping.infrastructure.adapters.out.services;

import com.miguelsperle.nexbuy.module.shipping.application.ports.out.services.FreightService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FreightServiceImpl implements FreightService {
    private final RestTemplate restTemplate;

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
        final Map<String, Object> fromMap = new HashMap<>();
        fromMap.put("postal_code", fromCep);

        final Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("from", fromMap);
        requestBody.put("to", toMap);
        requestBody.put("products", productMapList);

        final HttpHeaders httpHeaders = this.getHttpHeaders();

        final HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, httpHeaders);

        final ResponseEntity<String> response = this.restTemplate.exchange(this.url, HttpMethod.POST, request, String.class);

        return response.getBody();
    }

    private HttpHeaders getHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(this.token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("User-Agent", "Application " + this.email);

        return headers;
    }
}
