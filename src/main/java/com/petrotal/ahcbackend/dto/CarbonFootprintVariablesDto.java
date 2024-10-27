package com.petrotal.ahcbackend.dto;

public record CarbonFootprintVariablesDto(
        GasDto emissionFactorStationaryGasoline,
        GasDto emissionFactorStationaryDiesel,
        GasDto emissionFactorMobileGasoline,
        GasDto emissionFactorMobileDiesel,
        GasDto globalWarmingPotential
) {
}
