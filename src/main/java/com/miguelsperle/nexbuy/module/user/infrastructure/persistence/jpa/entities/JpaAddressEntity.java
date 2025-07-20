package com.miguelsperle.nexbuy.module.user.infrastructure.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.user.domain.entities.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
public class JpaAddressEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(name = "address_line", nullable = false, length = 100)
    private String addressLine;

    @Column(name = "address_number", nullable = false, length = 15)
    private String addressNumber;

    @Column(name = "zip_code", nullable = false, length = 9)
    private String zipCode;

    @Column(nullable = false, length = 40)
    private String neighborhood;

    @Column(nullable = false, length = 40)
    private String city;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(length = 100)
    private String complement;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaAddressEntity from(Address address) {
        return new JpaAddressEntity(
                address.getId(),
                address.getUserId(),
                address.getAddressLine(),
                address.getAddressNumber(),
                address.getZipCode(),
                address.getNeighborhood(),
                address.getCity(),
                address.getUf(),
                address.getComplement(),
                address.getCreatedAt()
        );
    }

    public Address toEntity() {
        return Address.with(
                this.id,
                this.userId,
                this.addressLine,
                this.addressNumber,
                this.zipCode,
                this.neighborhood,
                this.city,
                this.uf,
                this.complement,
                this.createdAt
        );
    }
}
