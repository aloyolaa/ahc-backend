package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.dto.CarbonFootprintDto;

public interface CarbonFootprintService {
    CarbonFootprintDto getCarbonFootprint();

    Double calculateCarbonFootprint(String fuelType, String consumptionType, Double consumption);
}
