package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.dto.CarbonFootprintDto;
import com.petrotal.ahcbackend.dto.CarbonFootprintVariablesDto;
import com.petrotal.ahcbackend.enumerator.EquipmentType;
import com.petrotal.ahcbackend.enumerator.FuelType;

public interface CarbonFootprintService {
    CarbonFootprintDto getCarbonFootprint();

    CarbonFootprintVariablesDto getAllVariables();

    Double calculateCarbonFootprint(FuelType fuelType, EquipmentType equipmentType, Double consumption);
}
