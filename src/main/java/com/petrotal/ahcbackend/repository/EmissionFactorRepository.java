package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.EmissionFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmissionFactorRepository extends JpaRepository<EmissionFactor, Long> {
    @Query("select e from EmissionFactor e where e.year = ?1 and e.fuelType = ?2 and e.consumptionType = ?3")
    Optional<EmissionFactor> findByYear(Integer year, String fuelType, String consumptionType);
}