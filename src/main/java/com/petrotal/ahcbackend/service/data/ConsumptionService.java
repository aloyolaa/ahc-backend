package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.enumerator.EquipmentType;
import com.petrotal.ahcbackend.enumerator.FuelType;

import java.util.List;

public interface ConsumptionService {
    Double calculateConsumption(FuelType fuelType, EquipmentType equipmentType, List<Data> data);
}
