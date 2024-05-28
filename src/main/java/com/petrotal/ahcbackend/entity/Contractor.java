package com.petrotal.ahcbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "contractor", uniqueConstraints = {
        @UniqueConstraint(name = "uc_contractor_name", columnNames = {"name"})
})
public class Contractor extends Base {
    public Contractor(Long id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Contractor{" +
                super.toString() +
                "}";
    }
}