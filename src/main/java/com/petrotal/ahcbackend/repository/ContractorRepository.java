package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractorRepository extends JpaRepository<Contractor, Long> {
}