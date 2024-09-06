package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractorRepository extends JpaRepository<Contractor, Long> {
    Optional<Contractor> findByName(String name);

    boolean existsByName(String name);
}