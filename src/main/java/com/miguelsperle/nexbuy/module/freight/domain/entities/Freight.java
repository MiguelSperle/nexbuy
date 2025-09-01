package com.miguelsperle.nexbuy.module.freight.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Freight {
    private final String id;
    private final String orderId;
    private final String trackingCode;
    private final String name;
    private final String companyName;
    private final BigDecimal price;
    private final Integer estimatedTime;
    private final Integer minTime;
    private final Integer maxTime;
    private final LocalDateTime createdAt;

    private Freight(
            String id,
            String orderId,
            String trackingCode,
            String name,
            String companyName,
            BigDecimal price,
            Integer estimatedTime,
            Integer minTime,
            Integer maxTime,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.orderId = orderId;
        this.trackingCode = trackingCode;
        this.name = name;
        this.companyName = companyName;
        this.price = price;
        this.estimatedTime = estimatedTime;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.createdAt = createdAt;
    }

    public static Freight newFreight(
            String orderId,
            String name,
            String companyName,
            BigDecimal price,
            Integer estimatedTime,
            Integer minTime,
            Integer maxTime
    ) {
        return new Freight(
                IdentifierUtils.generateUUID(),
                orderId,
                null,
                name,
                companyName,
                price,
                estimatedTime,
                minTime,
                maxTime,
                TimeUtils.now()
        );
    }

    public static Freight with(
            String id,
            String orderId,
            String trackingCode,
            String name,
            String companyName,
            BigDecimal price,
            Integer estimatedTime,
            Integer minTime,
            Integer maxTime,
            LocalDateTime createdAt
    ) {
        return new Freight(
                id,
                orderId,
                trackingCode,
                name,
                companyName,
                price,
                estimatedTime,
                minTime,
                maxTime,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public String getTrackingCode() {
        return this.trackingCode;
    }

    public String getName() {
        return this.name;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Integer getEstimatedTime() {
        return this.estimatedTime;
    }

    public Integer getMinTime() {
        return this.minTime;
    }

    public Integer getMaxTime() {
        return this.maxTime;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Freight{" +
                "id='" + this.id + '\'' +
                ", orderId='" + this.orderId + '\'' +
                ", trackingCode='" + this.trackingCode + '\'' +
                ", name='" + this.name + '\'' +
                ", companyName='" + this.companyName + '\'' +
                ", price=" + this.price +
                ", estimatedTime=" + this.estimatedTime +
                ", minTime=" + this.minTime +
                ", maxTime=" + this.maxTime +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
