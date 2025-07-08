package com.miguelsperle.nexbuy.module.user.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Address {
    private final String id;
    private final User user;
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

    public Address withAddressLine(String addressLine) {
        return new Address(
                this.id,
                this.user,
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
                this.user,
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
                this.user,
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
                this.user,
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
                this.user,
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
                this.user,
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
                this.user,
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
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getUf() {
        return uf;
    }

    public String getComplement() {
        return complement;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", addressLine='" + addressLine + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", uf='" + uf + '\'' +
                ", complement='" + complement + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
