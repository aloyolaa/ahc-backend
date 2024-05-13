package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.GlobalWarmingPotential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GlobalWarmingPotentialRepository extends JpaRepository<GlobalWarmingPotential, Long> {
    @Query("select g from GlobalWarmingPotential g where g.year = ?1")
    Optional<GlobalWarmingPotential> findByYear(Integer year);
}