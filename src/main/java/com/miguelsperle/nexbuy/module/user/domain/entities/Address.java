package com.miguelsperle.nexbuy.module.user.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Address {
    private final String id;
    private final String userId;
    private final String addressLine;
    private final String addressNumber;
    private final String zipCode;
    private final String neighborhood;
    private final String city;
    private final String uf;
    private final String complement;
    private final LocalDateTime createdAt;

    private Address(
            String id,
            String userId,
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
        this.userId = userId;
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
            String userId,
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
                userId,
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
            String userId,
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
                userId,
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

    public Address withAddressLine(String addressLine) {
        return new Address(
                this.id,
                this.userId,
                addressLine,
                this.addressNumber,
                this.zipCode,
                this.neighborhood,
                this.city,
                this.uf,
                this.complement,
                this.createdAt
        );
    }

    public Address withAddressNumber(String addressNumber) {
        return new Address(
                this.id,
                this.userId,
                this.addressLine,
                addressNumber,
                this.zipCode,
                this.neighborhood,
                this.city,
                this.uf,
                this.complement,
                this.createdAt
        );
    }

    public Address withZipCode(String zipCode) {
        return new Address(
                this.id,
                this.userId,
                this.addressLine,
                this.addressNumber,
                zipCode,
                this.neighborhood,
                this.city,
                this.uf,
                this.complement,
                this.createdAt
        );
    }

    public Address withNeighborhood(String neighborhood) {
        return new Address(
                this.id,
                this.userId,
                this.addressLine,
                this.addressNumber,
                this.zipCode,
                neighborhood,
                this.city,
                this.uf,
                this.complement,
                this.createdAt
        );
    }

    public Address withCity(String city) {
        return new Address(
                this.id,
                this.userId,
                this.addressLine,
                this.addressNumber,
                this.zipCode,
                this.neighborhood,
                city,
                this.uf,
                this.complement,
                this.createdAt
        );
    }

    public Address withUf(String uf) {
        return new Address(
                this.id,
                this.userId,
                this.addressLine,
                this.addressNumber,
                this.zipCode,
                this.neighborhood,
                this.city,
                uf,
                this.complement,
                this.createdAt
        );
    }

    public Address withComplement(String complement) {
        return new Address(
                this.id,
                this.userId,
                this.addressLine,
                this.addressNumber,
                this.zipCode,
                this.neighborhood,
                this.city,
                this.uf,
                complement,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getAddressLine() {
        return this.addressLine;
    }

    public String getAddressNumber() {
        return this.addressNumber;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public String getCity() {
        return this.city;
    }

    public String getUf() {
        return this.uf;
    }

    public String getComplement() {
        return this.complement;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + this.id + '\'' +
                ", userId=" + this.userId +
                ", addressLine='" + this.addressLine + '\'' +
                ", addressNumber='" + this.addressNumber + '\'' +
                ", zipCode='" + this.zipCode + '\'' +
                ", neighborhood='" + this.neighborhood + '\'' +
                ", city='" + this.city + '\'' +
                ", uf='" + this.uf + '\'' +
                ", complement='" + this.complement + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
