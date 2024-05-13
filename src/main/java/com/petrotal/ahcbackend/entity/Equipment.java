package com.petrotal.ahcbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "equipment")
public class Equipment extends Base {
    @Column(name = "type", nullable = false)
    private String type;

    public Equipment(Long id, String name, String type) {
        super(id, name);
        this.type = type;
    }
}