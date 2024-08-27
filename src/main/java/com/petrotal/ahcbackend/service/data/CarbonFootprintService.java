package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.dto.CarbonFootprintDto;
import com.petrotal.ahcbackend.entity.Data;

import java.util.List;

public interface CarbonFootprintService {
    CarbonFootprintDto getCarbonFootprint();

    Double calculateCarbonFootprint(String fuelType, String consumptionType, List<Data> data);
}
