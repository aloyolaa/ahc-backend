package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.GasDto;
import com.petrotal.ahcbackend.entity.EmissionFactor;
import com.petrotal.ahcbackend.entity.GlobalWarmingPotential;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GasMapper {
    GasDto toGasDto(EmissionFactor emissionFactor);

    GasDto toGasDto(GlobalWarmingPotential globalWarmingPotential);

    @Mapping(target = "year", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fuelType", ignore = true)
    @Mapping(target = "consumptionType", ignore = true)
    EmissionFactor toEmissionFactor(GasDto gasDto);

    @Mapping(target = "year", ignore = true)
    @Mapping(target = "id", ignore = true)
    GlobalWarmingPotential toGlobalWarmingPotential(GasDto gasDto);

    @Mapping(target = "year", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fuelType", ignore = true)
    @Mapping(target = "consumptionType", ignore = true)
    EmissionFactor partialUpdate(@MappingTarget EmissionFactor emissionFactor, GasDto gasDto);

    @Mapping(target = "year", ignore = true)
    @Mapping(target = "id", ignore = true)
    GlobalWarmingPotential partialUpdate(@MappingTarget GlobalWarmingPotential globalWarmingPotential, GasDto gasDto);
}
