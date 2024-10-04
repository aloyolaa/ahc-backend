package com.petrotal.ahcbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "data_signatory")
public class DataSignatory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "data_id", nullable = false)
    private Data data;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_signed", nullable = false)
    private Boolean isSigned = false;

    @PrePersist
    public void prePersist() {
        this.isSigned = false;
    }
}