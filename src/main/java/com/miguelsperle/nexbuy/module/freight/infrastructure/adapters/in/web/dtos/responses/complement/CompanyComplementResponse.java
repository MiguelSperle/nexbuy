package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.web.dtos.responses.complement;

public record CompanyComplementResponse(
        String name
) {
    public static CompanyComplementResponse from(String name) {
        return new CompanyComplementResponse(name);
    }
}
