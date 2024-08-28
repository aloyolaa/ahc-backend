package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.service.data.ConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumptionServiceImpl implements ConsumptionService {
    @Override
    public Double calculateConsumption(String fuelType, String consumptionType, List<Data> data) {
        return data
                .stream()
                .filter(d -> d.getEquipment().getType() != null && d.getEquipment().getType().equals(consumptionType) && d.getDescription().equals(fuelType))
                .mapToDouble(Data::getConsumption)
                .sum();
    }
}
