package com.miguelsperle.nexbuy.module.product.domain.entities;

import com.miguelsperle.nexbuy.module.product.domain.enums.BrandStatus;
import lombok.Data;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Brand {
    private final String id;
    private final String name;
    @With
    private final BrandStatus brandStatus;
    private final LocalDateTime createdAt;

    private Brand(String id, String name, BrandStatus brandStatus, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.brandStatus = brandStatus;
        this.createdAt = createdAt;
    }

    public static Brand newBrand(String name) {
        return new Brand(UUID.randomUUID().toString(), name, BrandStatus.ACTIVE, LocalDateTime.now());
    }

    public static Brand with(String id, String name, BrandStatus brandStatus, LocalDateTime createdAt) {
        return new Brand(id, name, brandStatus, createdAt);
    }
}
