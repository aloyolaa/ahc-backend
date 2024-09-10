package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.enumerator.EquipmentType;
import com.petrotal.ahcbackend.enumerator.FuelType;
import com.petrotal.ahcbackend.service.data.ConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumptionServiceImpl implements ConsumptionService {
    @Override
    public Double calculateConsumption(FuelType fuelType, EquipmentType equipmentType, List<Data> data) {
        /*return data
                .stream()
                .filter(d -> d.getEquipment().getType() != null && d.getEquipment().getType().equals(equipmentType) && d.getDataDetails().getDescription().equals(fuelType))
                .mapToDouble(Data::getConsumption)
                .sum();*/
        return 0.0;
    }
}
