package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.entity.Data;

import java.util.List;

public interface ConsumptionService {
    Double calculateConsumption(String fuelType, String consumptionType, List<Data> data);
}
