package com.miguelsperle.nexbuy.module.user.domain.entities;

import lombok.Data;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Address {
    private final String id;
    private final User user;
    @With
    private final String addressLine;
    @With
    private final String addressNumber;
    @With
    private final String zipCode;
    @With
    private final String neighborhood;
    @With
    private final String city;
    @With
    private final String uf;
    @With
    private final String complement;
    private final LocalDateTime createdAt;

    private Address(
            String id,
            User user,
            String addressLine,
            String addressNumber,
            String zipCode,
            String neighborhood,
            String city,
            String uf,
            String complement,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.user = user;
        this.addressLine = addressLine;
        this.addressNumber = addressNumber;
        this.zipCode = zipCode;
        this.neighborhood = neighborhood;
        this.city = city;
        this.uf = uf;
        this.complement = complement;
        this.createdAt = createdAt;
    }

    public static Address newAddress(
            User user,
            String addressLine,
            String addressNumber,
            String zipCode,
            String neighborhood,
            String city,
            String uf,
            String complement
    ) {
        return new Address(
                UUID.randomUUID().toString(),
                user,
                addressLine,
                addressNumber,
                zipCode,
                neighborhood,
                city,
                uf,
                complement,
                LocalDateTime.now()
        );
    }

    public static Address with(
            String id,
            User user,
            String addressLine,
            String addressNumber,
            String zipCode,
            String neighborhood,
            String city,
            String uf,
            String complement,
            LocalDateTime createdAt
    ) {
        return new Address(
                id,
                user,
                addressLine,
                addressNumber,
                zipCode,
                neighborhood,
                city,
                uf,
                complement,
                createdAt
        );
    }
}
