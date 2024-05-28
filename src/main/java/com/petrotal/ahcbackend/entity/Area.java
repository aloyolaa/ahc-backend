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
@Table(name = "area", uniqueConstraints = {
        @UniqueConstraint(name = "uc_area_name", columnNames = {"name"})
})
public class Area extends Base {
    public Area(Long id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Area{" +
                super.toString() +
                "}";
    }
}