package com.petrotal.ahcbackend.dto;

public record CarbonFootprintDto(
        Double stationaryMachineryDieselConsumption,
        Double stationaryMachineryDieselCalculate,
        Double stationaryMachineryGasolineConsumption,
        Double stationaryMachineryGasolineCalculate,
        Double mobileMachineryDieselConsumption,
        Double mobileMachineryDieselCalculate,
        Double mobileMachineryGasolineConsumption,
        Double mobileMachineryGasolineCalculate
) {
}
