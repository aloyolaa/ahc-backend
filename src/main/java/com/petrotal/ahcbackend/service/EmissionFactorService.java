package com.petrotal.ahcbackend.service;

import com.petrotal.ahcbackend.dto.GasDto;
import com.petrotal.ahcbackend.entity.EmissionFactor;

import java.util.Optional;

public interface EmissionFactorService {
    GasDto findByYear(Integer year, String fuelType, String consumptionType);

    Optional<EmissionFactor> findByYearOptional(Integer year, String fuelType, String consumptionType);

    void save(GasDto gasDto, String fuelType, String consumptionType);
}
