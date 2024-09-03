package com.petrotal.ahcbackend.dto;

public record CarbonFootprintDto(
        Double stationaryMachineryDieselConsumption,
        String stationaryMachineryDieselConsumptionStr,
        Double stationaryMachineryDieselCalculate,
        String stationaryMachineryDieselCalculateStr,
        Double stationaryMachineryGasolineConsumption,
        String stationaryMachineryGasolineConsumptionStr,
        Double stationaryMachineryGasolineCalculate,
        String stationaryMachineryGasolineCalculateStr,
        Double mobileMachineryDieselConsumption,
        String mobileMachineryDieselConsumptionStr,
        Double mobileMachineryDieselCalculate,
        String mobileMachineryDieselCalculateStr,
        Double mobileMachineryGasolineConsumption,
        String mobileMachineryGasolineConsumptionStr,
        Double mobileMachineryGasolineCalculate,
        String mobileMachineryGasolineCalculateStr
) {
}
