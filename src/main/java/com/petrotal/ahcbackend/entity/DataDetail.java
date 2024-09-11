package com.petrotal.ahcbackend.entity;

import com.petrotal.ahcbackend.enumerator.FuelType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "data_detail")
public class DataDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "condition")
    private Integer condition;

    @Column(name = "ordered_quantity", nullable = false)
    private Integer orderedQuantity;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "description", nullable = false)
    private FuelType description;

    @Column(name = "location")
    private String location;

    @Column(name = "unit_of_measurement", nullable = false)
    private String unitOfMeasurement;

    @Column(name = "quantity_shipped", nullable = false)
    private Integer quantityShipped;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "final_stock")
    private Integer finalStock;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "data_id")
    private Data data;
}