package com.petrotal.ahcbackend.dto;

public record CarbonFootprintDto(
        Double stationaryMachineryDiesel,
        Double stationaryMachineryGasoline,
        Double mobileMachineryDiesel
) {
}
