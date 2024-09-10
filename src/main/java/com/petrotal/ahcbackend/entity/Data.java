package com.petrotal.ahcbackend.entity;

import com.petrotal.ahcbackend.enumerator.FuelType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "data")
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "voucher_number")
    private String voucherNumber;

    @Column(name = "dispatch_date", nullable = false)
    private LocalDate dispatchDate;

    /*@Enumerated(EnumType.STRING)
    @Column(columnDefinition = "description", nullable = false)
    private FuelType description;*/

    /*@Column(name = "consumption", nullable = false)
    private Double consumption;*/

    /*@Column(name = "unit_of_measurement", nullable = false)
    private String unitOfMeasurement;*/

    @Column(name = "material_status")
    private String materialStatus;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @OneToMany(mappedBy = "data", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DataDetail> dataDetails = new LinkedHashSet<>();
}