package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findByName(String name);

    boolean existsByName(String name);
}