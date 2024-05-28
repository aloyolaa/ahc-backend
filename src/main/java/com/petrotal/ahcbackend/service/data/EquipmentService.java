package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.dto.EquipmentDto;
import com.petrotal.ahcbackend.entity.Equipment;

import java.util.List;

public interface EquipmentService {
    Equipment findById(Long id);

    Equipment findByName(String name);

    List<EquipmentDto> findAll();
}
