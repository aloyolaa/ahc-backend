package com.petrotal.ahcbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "contractor")
public class Contractor extends Base {
    public Contractor(Long id, String name) {
        super(id, name);
    }
}