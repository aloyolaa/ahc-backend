package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.dto.GasDto;
import com.petrotal.ahcbackend.entity.GlobalWarmingPotential;

import java.util.Optional;

public interface GlobalWarmingPotentialService {
    GasDto findByYear(Integer year);

    Optional<GlobalWarmingPotential> findByYearOptional(Integer year);

    void save(GasDto gasDto);
}
