package com.miguelsperle.nexbuy.module.order.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public class OrderDelivery {
    private final String id;
    private final String orderId;
    private final String addressLine;
    private final String addressNumber;
    private final String zipCode;
    private final String neighborhood;
    private final String city;
    private final String uf;
    private final String complement;
    private final LocalDateTime createdAt;

    private OrderDelivery(
            String id,
            String orderId,
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
        this.orderId = orderId;
        this.addressLine = addressLine;
        this.addressNumber = addressNumber;
        this.zipCode = zipCode;
        this.neighborhood = neighborhood;
        this.city = city;
        this.uf = uf;
        this.complement = complement;
        this.createdAt = createdAt;
    }

    public static OrderDelivery newOrderDelivery(
            String orderId,
            String addressLine,
            String addressNumber,
            String zipCode,
            String neighborhood,
            String city,
            String uf,
            String complement
    ) {
        return new OrderDelivery(
                IdentifierUtils.generateUUID(),
                orderId,
                addressLine,
                addressNumber,
                zipCode,
                neighborhood,
                city,
                uf,
                complement,
                TimeUtils.now()
        );
    }

    public static OrderDelivery with(
            String id,
            String orderId,
            String addressLine,
            String addressNumber,
            String zipCode,
            String neighborhood,
            String city,
            String uf,
            String complement,
            LocalDateTime createdAt
    ) {
        return new OrderDelivery(
                id,
                orderId,
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

    public String getId() {
        return this.id;
    }

    public String getOrderId() {
        return this.orderId;
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
        return "OrderDelivery{" +
                "id='" + this.id + '\'' +
                ", orderId='" + this.orderId + '\'' +
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
