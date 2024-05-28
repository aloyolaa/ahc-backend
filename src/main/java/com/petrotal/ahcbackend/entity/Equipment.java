package com.petrotal.ahcbackend.entity;

import jakarta.persistence.Column;
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
@Table(name = "equipment", uniqueConstraints = {
        @UniqueConstraint(name = "uc_equipment_name", columnNames = {"name"})
})
public class Equipment extends Base {
    @Column(name = "type", nullable = false)
    private String type;

    public Equipment(Long id, String name, String type) {
        super(id, name);
        this.type = type;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                super.toString() +
                ",  type='" + type + '\'' +
                '}';
    }
}