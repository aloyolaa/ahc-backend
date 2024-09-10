package com.petrotal.ahcbackend.entity;

import com.petrotal.ahcbackend.enumerator.EquipmentType;
import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "type", nullable = false)
    private EquipmentType type;

    public Equipment(Long id, String name, EquipmentType type) {
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