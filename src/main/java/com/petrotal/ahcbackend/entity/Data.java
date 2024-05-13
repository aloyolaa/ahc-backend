package com.petrotal.ahcbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "data")
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "voucher_number", nullable = false)
    private String voucherNumber;

    @Column(name = "dispatch_date", nullable = false)
    private LocalDate dispatchDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "consumption", nullable = false)
    private Double consumption;

    @Column(name = "unit_of_measurement", nullable = false)
    private String unitOfMeasurement;

    @ManyToOne(optional = false)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @ManyToOne(optional = false)
    @JoinColumn(name = "contractor_id", nullable = false)
    private Contractor contractor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @PrePersist
    public void prePersist() {
        this.unitOfMeasurement = "GALONES";
    }
}