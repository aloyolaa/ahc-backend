package com.petrotal.ahcbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
@Entity
@Table(name = "emission_factor")
public class EmissionFactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "co2", nullable = false)
    private Double co2;

    @Column(name = "ch4", nullable = false)
    private Double ch4;

    @Column(name = "n2o", nullable = false)
    private Double n2o;

    @Column(name = "year")
    private Integer year;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "consumption_type")
    private String consumptionType;

    @PrePersist
    private void prePersist() {
        this.year = Year.now().getValue();
    }
}