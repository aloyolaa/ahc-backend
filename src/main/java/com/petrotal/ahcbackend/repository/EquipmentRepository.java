package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}