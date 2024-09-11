package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findByName(String name);

    boolean existsByName(String name);

    @Query(value = "select e.id, e.name, e.type from equipment e", nativeQuery = true)
    List<Object[]> getAll();
}