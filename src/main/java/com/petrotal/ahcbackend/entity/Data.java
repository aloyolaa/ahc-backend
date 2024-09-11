package com.petrotal.ahcbackend.entity;

import com.petrotal.ahcbackend.enumerator.FuelType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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

    @Column(name = "voucher_number", nullable = false)
    private String voucherNumber;

    @Column(name = "dispatch_date", nullable = false)
    private LocalDate dispatchDate;

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
    private List<DataDetail> dataDetails = new ArrayList<>();
}